import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {UploadService} from '../../services/upload.service'

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {


  form: FormGroup;
  error: string;
  uploadResponse = { status: '', message: '' };
  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];


  constructor(private formBuilder: FormBuilder, private uploadService: UploadService) { }

  ngOnInit(): void {
    /*this.uploadService.getPdfFormFields(processInstaceId).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
            console.log(this.enumValues)
          }
        });
      },
      (err) => {
        console.log("Error occured");
      }
    )*/
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
     // this.form.get('pdf').setValue(file);
      console.log(file);
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.form.get('pdf').value);

    this.uploadService.upload(formData, this.formFieldsDto.taskId).subscribe(
      (res: any) => {
        this.uploadResponse = res
        if (this.uploadResponse.status === 'finish') {
        }
      },
      (err) => this.error = err
    );
  }

}
