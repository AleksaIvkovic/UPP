import { Component, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { UserService} from '../../services/user.service'

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  formFields = [];
  enumValues = [];
  processInstance;
  formFieldsDto = null;

  registerForm: FormGroup = new FormGroup({});
  formControls: FormControl[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getRegisterForm().subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.map( (field) => {
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

          if (field.id == "email") {
            validators.push(Validators.email);
          }

          this.registerForm.controls[field.id].setValidators(
            validators
          );

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
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
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    this.userService.submitRegisterForm(o, this.formFieldsDto.taskId).subscribe(
      (res) => {
        alert('Registration successful');
    }, error => {
      console.log(error);
    });
  }
}
