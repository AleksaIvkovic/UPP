import { htmlAstToRender3Ast } from '@angular/compiler/src/render3/r3_template_transform';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AbstractControl, Form, FormControl, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { UploadService } from 'src/app/services/upload.service';
import { UserService} from '../../services/user.service'

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
  files = [];

  registerForm: FormGroup = new FormGroup({});
  enumForm = new Map();
  formControls: FormControl[] = [];
  uploadResponse = { status: '', message: '' };

  isReader = false;
  isBetaReader = false;
  isWriter = false;
  submitWork = false;

  constructor( 
    private userService: UserService,
    private uploadService: UploadService,
    private route: ActivatedRoute,
    private router: Router) { }
  
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
    else if(this.router.url.includes('beta')){
      this.isBetaReader = true;
      this.initForm(JSON.parse(sessionStorage.getItem('betaForm')));
    }
    else if(this.router.url.includes('writer')){
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
    else if(this.router.url.includes('submit')){
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
  }

  initForm(res){
    console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.map( (field) => {

          if(!field.type.name.includes("multipleEnum")){
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
                //  case 'email' : {
                //   validators.push(Validators.email);
                //   break;
                // }
                case 'minlength' : {
                  validators.push(Validators.minLength(<number>validation.configuration));
                  break;
                }
              }
            });

            this.registerForm.controls[field.id].setValidators(
              validators
            );

            // if(field.type.name == "boolean"){
            //   let element = document.getElementById(field.id);
            //   console.log(element);
            //   //element.nodeValue = field.defaultValue.toString();
            //   //temp.setValue(field.defaultValue);
            // }
          }
          else{
            this.enumValues.set(field.id, Object.keys(field.type.values));

            let tempForm = new FormGroup({}, this.checkArray);

            for (let [key, value] of this.enumValues) {
              if(key == field.id){
                for(let genreId of value){
                  tempForm.addControl(genreId, new FormControl(false));
                }
              }
            }

            this.registerForm.addControl(
              field.id, tempForm
            );
          }
        });
        console.log(this.registerForm);

        // this.formFields.map( (field) =>{
        //   field.validationConstraints.map( (validation) => {
        //     switch(validation){
        //        case 'required' : {
        //           break;
        //        }
        //     }
        //   })

        //   if( field.type.name=='enum'){
        //     this.enumValues = Object.keys(field.type.values);
        //   }
        // });
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      if(property == "files"){
        value[property] = this.files;
      }
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    if(this.submitWork){

      let tempFiles = [];
      for(let file of this.files){
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
          this.uploadResponse = res
          if (this.uploadResponse.status === 'finish') {
            console.log("Uspesan upload.")
            this.userService.submitWork(o, this.formFieldsDto.taskId).subscribe(
              (res: any) =>{
                alert("Successful work submission.")
              },
            (err) => console.log(err)
            );
          }
        },
        (err) => {
          console.log(err);
        }
      )

      // this.uploadService.upload(formData, this.formFieldsDto.taskId).subscribe(
      //   (res: any) => {
      //     this.uploadResponse = res
      //     if (this.uploadResponse.status === 'finish') {
      //       console.log("Uspesan upload.")
      //       this.userService.submitWork(o, this.formFieldsDto.taskId).subscribe(
      //         (res: any) =>{
      //           alert("Successful work submission.")
      //         },
      //       (err) => console.log(err)
      //       );
      //     }
      //     },
      //   (err) => console.log(err)
      // );
    }
    else if(!this.isBetaReader){
      this.userService.submitRegisterForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Registration successful. Verification email has been sent to your address.');
          }
          else{
            sessionStorage.setItem('betaForm', JSON.stringify(res));
            this.router.navigate(['../','register-beta']);
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
      return {'none are chosen': true}
    }
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      for(let file of event.target.files)
        this.files.push(file);
     // this.registerForm.controls["files"].setValue((<Array>this.registerForm.controls["files"].value));
      console.log(this.files.length);

    }
  }

}
