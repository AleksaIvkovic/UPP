import { Component, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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

  registerForm: FormGroup = new FormGroup({});
  enumForm = new Map();
  formControls: FormControl[] = [];

  isReader = false;
  isBetaReader = false;
  isWriter = false;

  constructor( 
    private userService: UserService,
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
    }
  }

  initForm(res){
    console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.map( (field) => {

          if(field.type.name != "multipleEnum_genres"){
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
          }
          else{
            this.enumValues.set(field.id, Object.keys(field.type.values));

            let tempForm = new FormGroup({});

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
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    if(this.isReader){
      this.userService.submitRegisterForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          if(res == null){
            alert('Registration successful');
          }
          else{
            sessionStorage.setItem('betaForm',JSON.stringify(res));
            this.router.navigate(['../','register-beta']);
          }
      }, error => {
        console.log(error);
      });
    }
    else if(this.isBetaReader){
      this.userService.submitBetaForm(o, this.formFieldsDto.taskId).subscribe(
        (res) => {
          alert('Registration successful');
      }, error => {
        console.log(error);
      });
    }
  }
}
