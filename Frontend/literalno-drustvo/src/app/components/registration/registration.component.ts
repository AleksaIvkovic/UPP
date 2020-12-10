import { Component, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, NgForm } from '@angular/forms';
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
  f: NgForm

  constructor(private repositoryService: UserService) { }

  ngOnInit(): void {
    this.repositoryService.getRegisterForm().subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        //this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.map( (field) =>{
          field.validationConstraints.map( (validation) => {
            switch(validation){
               case 'required' : {
                  break;
               }
            }
          })

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
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
  }
}
