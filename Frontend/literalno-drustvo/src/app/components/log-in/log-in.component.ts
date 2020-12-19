import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserService } from 'src/app/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {

  logInForm: FormGroup;
  hide = true;
  username: string;
  password: string;
  socialProvider = "google";

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {username: string, password: string},
    private dialogRef:MatDialogRef<LogInComponent>,
    private userService: UserService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.logInForm = new FormGroup({
      'username': new FormControl(null, Validators.required),
      'password': new FormControl(null, Validators.required),
    });
  }

  onLogin() {
    this.userService.login(this.logInForm.value['username'], this.logInForm.value['password'])
    .subscribe(
      (res: any) => {
        localStorage.setItem('username', res.username);
        localStorage.setItem('role', res.role);
        this.dialogRef.close('success');
      },
      err => {
        if (err.status == 400) {
          this._snackBar.open(err.error.message, 'OK', {duration: 5000,});
          this.logInForm.patchValue({
            username: '',
            password: ''
          });
          this.logInForm.markAsPristine();
          this.logInForm.markAsUntouched();
        }
        else
          console.log(err);
      }
    );
  }

}
