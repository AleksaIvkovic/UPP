import { htmlAstToRender3Ast } from '@angular/compiler/src/render3/r3_template_transform';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AbstractControl, Form, FormControl, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { disableDebugTools } from '@angular/platform-browser';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { UploadService } from 'src/app/services/upload.service';
import { UserService} from '../../services/user.service'
import { PlagiarismService} from '../../services/plagiarism.service'

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  formFields = [];
  enumValues = new Map();
  processInstance;
  formFieldsDto = null;
  files = new Map();
  filesString = [];
 

  registerForm: FormGroup = new FormGroup({});
  formControls: FormControl[] = [];
  uploadResponse = { status: '', message: '' };

  isReader = false;
  isBetaReader = false;
  isWriter = false;
  submitWork = false;
  submitNewBook = false;
  isTask = false;
  isCommitee = false;
  synopsisReview = false;
  explanation = false;
  payMembership = false;
  manuscriptUpload = false;
  plagiarismReview = false;
  downloadManuscript = false;
  fileAnAppeal = false;
  chooseEditors = false;
  editorPlagiarismBookReview = false;
  commiteReview = false;

  constructor( 
    private userService: UserService,
    private uploadService: UploadService,
    private route: ActivatedRoute,
    private router: Router,
    private plagiarismService: PlagiarismService) {}
  
  ngOnInit(): void {

    if(this.router.url.includes('register-reader')){
      this.isReader = true;
      this.userService.getRegisterForm().subscribe(
        res => {
          this.initForm(res);
        },
        err => {
          console.log("Error occured");
        }
      );
    }
    else if(this.router.url.includes('register-beta')){
      this.isBetaReader = true;
      this.initForm(JSON.parse(sessionStorage.getItem('betaForm')));
    }
    else if(this.router.url.includes('register-writer')){
      this.isWriter = true;
      this.userService.getRegisterForm().subscribe(
        res => {
          this.initForm(res);
        },
        err => {
          console.log("Error occured");
        }
      );
    }
    else if(this.router.url.includes('submit-work')){
      this.isWriter = true;
      this.submitWork = true;
      sessionStorage.setItem("processId", this.router.url.split("/")[3] );
      this.userService.getRegisterForm().subscribe(
        res => {
          this.initForm(res);
        },
        err => {
          console.log(err);
          console.log("Error occured");
        }
      );
    }
    else if(this.router.url.includes('submit-new-book')){
      this.submitNewBook = true;
     this.userService.getRegisterForm().subscribe(
        res => {
          this.initForm(res);
        },
        err => {
          console.log(err);
          console.log("Error occured");
        }
      );
    }
    else if (this.router.url.includes('tasks')) {
      this.route.params.subscribe(
        (params: Params) => {
          if(params['taskName'] == 'Submit works'){
            this.submitWork = true;
          }
          else if(params['taskName'] == 'Review writer for membership'){
            this.isCommitee = true;
            this.isTask = true;
          } else if(params['taskName'] == 'Deliver more work'){
            this.submitWork = true;
          } else if(params['taskName'] == 'Synopsis review') {
            this.synopsisReview = true;
          } else if(params['taskName'] == 'Give an explanation') {
            this.explanation = true;
          } else if (params['taskName'] == 'Pay membership') {
            this.payMembership = true;
          } else if (params['taskName'] == 'Manuscript upload') {
            this.manuscriptUpload = true;
          }else if (params['taskName'] == 'Plagiarism review') {
            this.plagiarismReview = true;
          }else if (params['taskName'] == 'Download manuscript') {
            this.downloadManuscript = true;
          } else if(params['taskName'] == 'Choose editors'){
            this.chooseEditors = true;
          } else if(params['taskName'] == 'Review books'){
            this.editorPlagiarismBookReview = true;
          } else if(params['taskName'] == 'Committee review'){
            this.commiteReview = true;
          }
          
          this.userService.getTask(params['taskId']).subscribe(
            res => {
              this.initForm(res);
            },
            err => {
              console.log(err);
            }
          )
        },
        err => {
          console.log(err);
        }
      )
    }
    else if (this.router.url.includes('file-an-appeal')){
      this.fileAnAppeal = true;
      this.userService.getRegisterForm().subscribe(
        res => {
          this.initForm(res);
        },
        err => {
          console.log(err);
          console.log("Error occured");
        }
      );
    }
  }

  initForm(res){
    console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.map( (field) => {

          if(field.type.name.includes("multipleEnum")){
            this.enumValues.set(field.id, Object.keys(field.type.values));

            let tempForm = new FormGroup({}, this.checkArray);

            for (let [key, value] of this.enumValues) {
              if(key == field.id){
                for(let enumId of value){
                  tempForm.addControl(enumId, new FormControl(false));
                }
              }
            }

            this.registerForm.addControl(
              field.id, tempForm
            );
          }
          else if(field.type.name.includes("files")){
            let minFiles = <number>field.type.name.split('_')[1];
            let maxFiles = <number>field.type.name.split('_')[2]
            let tempForm = new FormControl([], this.checkFiles(minFiles, maxFiles));
            this.files.set(field.id, []);
            this.registerForm.addControl(
              field.id, tempForm
            );
          }
          else if(field.type.name.includes("notEditableEnum")){
            this.enumValues.set(field.id, Object.keys(field.type.values));

            let tempControl = new FormControl([]);
            let temp = [];

            for (let [key, value] of this.enumValues) {
              if(key == field.id){
                for(let enumId of value){
                  temp.push(field.type.values[enumId]);
                }
              }
            }

            tempControl.setValue(temp);

            this.registerForm.addControl(
              field.id, tempControl
            );
          }
          else if(field.type.name == "string_labels"){
            this.enumValues.set(field.id, Object.keys(field.defaultValue));
          }
          else if(field.type.name.includes("label")){
            this.enumValues.set(field.id, Object.keys(field.type.values));

            let tempControl = new FormControl([]);
            let temp = [];

            for (let [key, value] of this.enumValues) {
              if(key == field.id){
                for(let enumId of value){
                  temp.push(field.type.values[enumId]);
                }
              }
            }

            tempControl.setValue(temp);

            this.registerForm.addControl(
              field.id, tempControl
            );
          }
          else{
            let temp = new FormControl(field.defaultValue);
            this.formControls.push(temp);
            this.registerForm.addControl(field.id, temp);
            
            let validators = [];

            field.validationConstraints.map( (validation) => {
              switch(validation.name){
                case 'required' : {
                  validators.push(Validators.required);
                  break;
                }
                case 'minlength' : {
                  validators.push(Validators.minLength(<number>validation.configuration));
                  break;
                }
              }
            });

            this.registerForm.controls[field.id].setValidators(
              validators
            );
          }
        });
        console.log(this.registerForm);
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    if(this.submitWork){

      for(let filesList of this.files.values()){
        let tempFiles = [];

        for(let file of filesList){
          const fd = new FormData();
          fd.append('file',file);
          tempFiles.push(fd);
        }
  
        let requests = [];
        
        for(let fd of tempFiles){
          requests.push(this.uploadService.upload(fd, this.formFieldsDto.taskId));
        }
  
        forkJoin(requests).subscribe(
          (res:any) => {
            console.log("Successful upload.")
            this.userService.submitWork(o, this.formFieldsDto.taskId).subscribe(
              (res: any) =>{
                alert("Successful work submission.")
              },
            (err) => console.log(err)
            );
          },
          (err) => {
            console.log(err);
          }
        )
      }
    } else if(this.manuscriptUpload) {
      for(let filesList of this.files.values()){
        let tempFiles = [];

        for(let file of filesList){
          const fd = new FormData();
          fd.append('file',file);
          tempFiles.push(fd);
        }
  
        let requests = [];
        
        for(let fd of tempFiles){
          requests.push(this.uploadService.upload(fd, this.formFieldsDto.taskId));
        }
  
        forkJoin(requests).subscribe(
          (res:any) => {
            console.log("Successful upload.")
            this.userService.submitManuscript(o, this.formFieldsDto.taskId).subscribe(
              (res: any) =>{
                alert("Successful work submission.")
              },
            (err) => console.log(err)
            );
          },
          (err) => {
            console.log(err);
          }
        )
      }
    }
     else if (this.payMembership) {
      this.userService.submitPaymentDetails(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          alert('Payment details submitted succesfully.');
      }, error => {
        console.log(error);
        alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
      });
    }
    else if(this.submitNewBook){
      this.userService.submitBookSynopsis(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Book synopsis successfully submited.');
          }
          else{
           
          }
      }, (error : any)  => {
        console.log(error);
        alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
      });
    }
    else if (this.synopsisReview) {
      this.userService.submitBookSynopsisReview(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Book synopsis review successfully submited.');
          }
          else{
           
          }
      }, (error : any)  => {
        console.log(error);
      });
    }
    else if(this.plagiarismReview){
      this.userService.submitPlagiarismReview(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Plagiarism review successfully submited.');
          }
          else{
           
          }
      }, (error : any)  => {
        console.log(error);
      });
    }
    else if (this.explanation) {
      this.userService.submitExplanation(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Explanation successfully submited.');
          }
          else{
           
          }
      }, (error : any)  => {
        console.log(error);
      });
    }
    else if(this.downloadManuscript){
      this.userService.submitManuscriptReview(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Manuscript review successfully submited.');
          }
          else{
           
          }
      }, (error : any)  => {
        console.log(error);
      });
    }
    else if(this.isTask && this.isCommitee) {
      this.userService.submitVoteForNewWriter(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          alert('Vote successful.');
      }, error => {
        console.log(error);
        alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
      });
    }
    else if(this.fileAnAppeal){
      this.plagiarismService.submitAppealForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          alert('Success');
        },
        err => {
          console.log(err);
        }
      )
    }
    else if(this.chooseEditors){
      this.plagiarismService.submitChosenEditorsForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          alert('Success');
        },
        err => {
          console.log(err);
        }
      )
    }
    else if(this.editorPlagiarismBookReview){
      this.plagiarismService.submitEditorReviewForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          alert('Success');
        },
        err => {
          console.log(err);
        }
      )
    }
    else if(this.commiteReview){
      this.plagiarismService.submitCommitteeReviewForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          alert('Success');
        },
        err => {
          console.log(err);
        }
      )
    }
    else if(!this.isBetaReader){
      this.userService.submitRegisterForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Registration successful. Verification email has been sent to your address.');
          }
          else{
            sessionStorage.setItem('betaForm', JSON.stringify(res));
            this.router.navigate(['main/register-beta']);
          }
      }, (error : any)  => {
        console.log(error);
        alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
      });
    }
    else {
      this.userService.submitBetaForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          alert('Registration successful. Verification email has been sent to your address.');
      }, error => {
        console.log(error);
        alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
      });
    }
  }

  checkArray(group: FormGroup): {[s:string]:boolean}{
    let found = false;
    Object.keys(group.controls).forEach(key => {
      if(group.controls[key].value)
      {
        found = true;
      }
    });
    if(found){
      return null
    }
    else{
      return {'None are chosen': true}
    }
  }

   checkFiles(min: number, max : number): ValidatorFn {
    return (control: FormControl): {[key: string]: any} | null => {
      if((<File[]>control.value).length < min || (<File[]>control.value).length > max)
        return {'More files are needed' : true};
      else
        return null;
    };
  }

  onFileChange(event, id) {
    if (event.target.files.length > 0) {
      for(let file of event.target.files){
        this.filesString = (<String[]>this.registerForm.controls[id].value);
        this.filesString.push(file.name);
        this.registerForm.controls[id].setValue(this.filesString);
        let listOfFiles;
        listOfFiles = (<File[]>this.files.get(id));
        listOfFiles.push(file); 
        this.files.set(id,listOfFiles);
      }
    }
  }

  download(name){
    this.uploadService.download(name).subscribe(
      (blob: any) => {
        const a = document.createElement('a')
        const objectUrl = URL.createObjectURL(blob)
        a.href = objectUrl
        a.download = name;
        a.click();
        URL.revokeObjectURL(objectUrl);
      },
      (error) => {
        console.log('Error: ' + error);;
      });
  }

  RemoveFile(name, fieldId){

    this.filesString = (<String[]>this.registerForm.controls[fieldId].value);
    for(let s of this.filesString){
      console.log(s);
    }
    let id = this.filesString.findIndex(element => {
      if(element == name){
        return true;
      }
    });
    this.filesString.splice(id,1);
    this.registerForm.controls[fieldId].setValue(this.filesString);

    let listOfFiles;
    listOfFiles = (<File[]>this.files.get(fieldId));
    id = listOfFiles.findIndex(element => {
      if(element.name == name){
        return true;
      }
    });
    listOfFiles.splice(id,1);
    this.files.set(id,listOfFiles);

    console.log(this.registerForm);
    console.log(this.files);
  }
}