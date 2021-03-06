import { htmlAstToRender3Ast } from '@angular/compiler/src/render3/r3_template_transform';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AbstractControl, Form, FormControl, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { disableDebugTools } from '@angular/platform-browser';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { UploadService } from 'src/app/services/upload.service';
import { UserService} from '../../services/user.service'
import { PlagiarismService} from '../../services/plagiarism.service'
import { parseI18nMeta } from '@angular/compiler/src/render3/view/i18n/meta';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  formFields = [];
  enumValues = new Map();
  processInstance;
  formFieldsDto = null;
  files = new Map();
  filesString = [];
 

  registerForm: FormGroup = new FormGroup({});
  formControls: FormControl[] = [];
  uploadResponse = { status: '', message: '' };

  tasksCamundaForm = ['Decide if more changes are needed', 'Download and lector', 'Editor approval'];
  tasksCamundaFormWithVariable = ['Give an explanation', 'Pay membership', 'Select beta readers', 'Book synopsis'];
  tasksCamundaFormWithReturnTask = ['Synopsis review', 'Plagiarism review', 'Download manuscript', 'Send to beta decision'];
  submitWorksTasks = ['Submit works', 'Make changes', 'Deliver more work', 'Manuscript upload']

  isCamundaForm = false;
  isCamundaFormWithVariable = false;
  isCamundaFormWithReturnTask = false;
  variableName = '';
  nextTaskName = '';

  isReader = false;
  isBetaReader = false;
  isWriter = false;
  submitWork = false;
  isTask = false;
  isCommitee = false;
  fileAnAppeal = false;
  chooseEditors = false;
  editorPlagiarismBookReview = false;
  commiteReview = false;
  chooseSubstitute = false;
  commentManuscript = false;

  constructor( 
    private userService: UserService,
    private uploadService: UploadService,
    private route: ActivatedRoute,
    private router: Router,
    private plagiarismService: PlagiarismService,
    private _snackBar: MatSnackBar,
    ) {}

  ngOnInit(): void {
    this.setBooleansToFalse();
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
    else if(this.router.url.includes('submit-new-book')){
      this.isCamundaFormWithVariable = true;
      this.variableName = 'newPublishedBookForm';
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
          if (this.tasksCamundaForm.includes(params['taskName'])) {
            this.isCamundaForm = true;
          } else if (this.tasksCamundaFormWithReturnTask.includes(params['taskName'])) {
            this.isCamundaFormWithReturnTask = true;

            switch (params['taskName']) {
              case 'Synopsis review':
                this.nextTaskName = 'Give an explanation';
                break;
              case 'Plagiarism review':
                this.nextTaskName = 'Download manuscript';
                break;
              case 'Download manuscript':
                this.nextTaskName = 'Send to beta decision | Give an explanation';
                break;
              case 'Send to beta decision':
                this.nextTaskName = 'Select beta readers';
                break;
            }
          } else if (this.tasksCamundaFormWithVariable.includes(params['taskName'])) {
            this.isCamundaFormWithVariable = true;
            switch (params['taskName']) {
              case 'Give an explanation':
                this.variableName = 'explanation';
                break;
              case 'Pay membership':
                this.variableName = 'creditCard';
                break;
              case 'Select beta readers':
                this.variableName = 'selectedBetaReadersForm';
                break;
              case 'Book synopsis':
                this.variableName = 'newPublishedBookForm';
                break;
            }
          } else if(this.submitWorksTasks.includes(params['taskName'])){
            this.submitWork = true;
          } else if(params['taskName'] == 'Review writer for membership'){
            this.isCommitee = true;
            this.isTask = true;
          } else if(params['taskName'] == 'Choose editors'){
            this.chooseEditors = true;
          } else if(params['taskName'] == 'Review books'){
            this.editorPlagiarismBookReview = true;
          } else if(params['taskName'] == 'Committee review'){
            this.commiteReview = true;
          } else if(params['taskName'] == 'Choose substitute'){
            this.chooseSubstitute = true;
          } else if(params['taskName'] == 'Comment manuscript'){
            this.commentManuscript = true;
          } else if(params['taskName'] == 'File an appeal'){
            this.fileAnAppeal = true;
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
    this.formFieldsDto = res;
    this.formFields = res.formFields;
    this.processInstance = res.processInstanceId;
    this.formFields.map( (field) => {

      if(field.type.name.includes("multipleEnum")){
        this.enumValues.set(field.id, Object.keys(field.type.values));
      
        let min = 0;
        let exact = 0;
        if(field.type.name.split('_')[2] == 'x'){
          exact = <number>field.defaultValue;
        }
        else{
          min = <number>field.type.name.split('_')[2];
        }
        let tempForm = new FormGroup({}, this.checkArray(min, exact));

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
        this.enumValues.set(field.id, field.defaultValue);
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
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      o.push({fieldId : property, fieldValue : value[property]});
    }

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
            this.userService.submitCamundaFormWithVariable(o, this.formFieldsDto.taskId, 'worksToStore').subscribe(
              (res: any) =>{
                this._snackBar.open('Successful work submission.', 'OK', {duration: 5000,});
                this.userService.onTaskChange('');
                this.router.navigate(['../../'], {relativeTo: this.route});
              },
            err => {
              console.log(err);
            }
            );
          },
          err => {
            console.log(err);
          }
        )
      }
    } 
    else if (this.isCamundaForm) {
      this.userService.submitCamundaForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
      }, error => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        //alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
        //this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
    else if (this.isCamundaFormWithReturnTask) {
      this.userService.submitCamundaFormWithReturnTask(o, this.formFieldsDto.taskId, this.nextTaskName).subscribe(
        (res: any) => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          if(res != null){
            this.router.navigate(['../../', res.id, res.name], {relativeTo: this.route});
          } else {
            this.router.navigate(['../../'], {relativeTo: this.route});
            if (this.nextTaskName == 'Select beta readers') {
              this._snackBar.open('There are no registered beta readers for given genre.', 'OK', {duration: 5000,});
            }
          }
      }, error => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        //alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
        //this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
    else if (this.isCamundaFormWithVariable) {
      this.userService.submitCamundaFormWithVariable(o, this.formFieldsDto.taskId, this.variableName).subscribe(
        (res) => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
      }, error => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        //alert("Field " + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString());
        //this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
    else if (this.commentManuscript) {
      this.userService.submitCommentManuscript(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
      }, error => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
    else if(this.isTask && this.isCommitee) {
      this.userService.submitVoteForNewWriter(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
      }, error => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
    else if(this.fileAnAppeal){
      this.plagiarismService.submitAppealForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
        },
        error => {
          this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        }
      )
    }
    else if(this.chooseEditors){
      this.plagiarismService.submitChosenEditorsForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
        },
        error => {
          this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        }
      )
    }
    else if(this.editorPlagiarismBookReview){
      this.plagiarismService.submitEditorReviewForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
        },
        error => {
          this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        }
      )
    }
    else if(this.commiteReview){
      this.plagiarismService.submitCommitteeReviewForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
        },
        error => {
          this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        }
      )
    }
    else if(this.chooseSubstitute){
      this.plagiarismService.submitSubstituteChoiceForm(o, this.formFieldsDto.taskId).subscribe(
        res => {
          this._snackBar.open('Action successful.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
        },
        error => {
          this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
        }
      )
    }
    else if (this.isWriter) {
      this.userService.submitRegisterForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
            this._snackBar.open('Registration successful. Verification email has been sent to your address.', 'OK', {duration: 5000,});
            this.router.navigate(['../../'], {relativeTo: this.route});
      }, (error : any)  => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
    else if(!this.isBetaReader){
      this.userService.submitRegisterForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            this._snackBar.open('Registration successful. Verification email has been sent to your address.', 'OK', {duration: 5000,});
            this.router.navigate(['../../'], {relativeTo: this.route});
          }
          else{
            sessionStorage.setItem('betaForm', JSON.stringify(res));
            this.router.navigate(['main/register-beta']);
          }
      }, (error : any)  => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
    else {
      this.userService.submitCamundaFormWithVariable(o, this.formFieldsDto.taskId, 'betaGenresForm').subscribe(
        (res) => {
          this._snackBar.open('Registration successful. Verification email has been sent to your address.', 'OK', {duration: 5000,});
          this.userService.onTaskChange('');
          this.router.navigate(['../../'], {relativeTo: this.route});
      }, error => {
        this._snackBar.open('Field ' + error.error.fieldType.toString() + " is invalid. Cause: " + error.error.validatorType.toString(), 'OK', {duration: 5000,});
      });
    }
  }

  checkArray(min: number, exact: number): ValidatorFn {
    return (group: FormGroup): {[key: string]: any} | null => {
      let found = 0;
      Object.keys(group.controls).forEach(key => {
        if(group.controls[key].value)
        {
          found += 1;
        }
      });
      if(min != 0){
        if(found >= min){
          return null
        }
        else{
          let str = 'Must choose more than '.concat(min.toString());
          return { str : true}
        }
      }
      else{
        if(found == exact){
          return null
        }
        else{
          let str = 'Must choose exactly '.concat(exact.toString());
          return { str : true}
        }
      }
    };
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

  download(name) {
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
        console.log('Error: ' + error);
        alert(error);
      });
  }

  RemoveFile(name, fieldId) {

    this.filesString = (<String[]>this.registerForm.controls[fieldId].value);

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
  }

  setBooleansToFalse() {
    this.isReader = false;
    this.isBetaReader = false;
    this.isWriter = false;
    this.submitWork = false;
    this.isTask = false;
    this.isCommitee = false;
    this.fileAnAppeal = false;
    this.chooseEditors = false;
    this.editorPlagiarismBookReview = false;
    this.commiteReview = false;
    this.chooseSubstitute = false;
    this.commentManuscript = false;
    this.isCamundaForm = false;
    this.isCamundaFormWithVariable = false;
    this.isCamundaFormWithReturnTask = false;
  }
}