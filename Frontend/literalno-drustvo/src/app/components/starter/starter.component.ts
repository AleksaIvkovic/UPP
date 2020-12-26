import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { SysUser } from '../../models/sysUser.model';
import { StarterService } from '../../services/starter.service';
import { LogInComponent } from '../log-in/log-in.component';

@Component({
  selector: 'app-starter',
  templateUrl: './starter.component.html',
  styleUrls: ['./starter.component.css']
})
export class StarterComponent implements OnInit {
  user: SysUser = null;
  loggedIn: boolean = false;

  constructor(
    private activeRoute: ActivatedRoute,
    private router: Router,
    private starterService: StarterService,
    private authService: AuthService,
    private logInDialog: MatDialog,
  ) { }

  ngOnInit(): void {
  }

  StartReaderRegistrationProcess(){
    this.starterService.startReaderRegistration().subscribe(
      (res) => {
        sessionStorage.setItem("processId", res.processId );
        this.router.navigate(['register-reader'], {relativeTo : this.activeRoute});
      },
      (err) => {
        console.log(err);
      }
    )
  }

  StartWriterRegistrationProcess(){
    this.starterService.startWriterRegistration().subscribe(
      (res) => {
        sessionStorage.setItem("processId", res.processId );
        this.router.navigate(['register-writer'], {relativeTo : this.activeRoute});
      },
      (err) => {
        console.log(err);
      }
    )
  }


  LogIn(){
    let dialogRef = this.logInDialog.open(
      LogInComponent, {
        data: {username: '', password: ''}
      }
    );

    dialogRef.afterClosed()
    .subscribe(
      (result) => {
        if (result === "success") {
          //this.username = localStorage.getItem('username');
          //this.role = localStorage.getItem('role');
          this.authService.getLoggedInUser().subscribe(
            (res : SysUser) => {
              alert("Uspesno logovanje");
              sessionStorage.setItem("loggedInUser", JSON.stringify(res));
              this.loggedIn = true;
              this.router.navigate(['/main']);
            }, 
            error => {
              console.log(error);
            }
          );
          //if (this.username === undefined) {
          //  return;
          //}
          // this.userService.getUser().subscribe(
          //   (response: any) => {
          //     this.user = Object.assign
          //     (new User(this.role, '', '', '', '', '', '', ''), response);
          //     localStorage.setItem('user', JSON.stringify(this.user));
          //     localStorage.setItem('company', response['company']);
          //     this.isLoggedIn = true;
          //     this.checkUser();
          //   },
          //   error => {
          //     console.log(error);
          //   }
          // );
          
        }
    })
  }

  LogOut(){
    sessionStorage.clear();
    this.loggedIn = false;
    this.router.navigate(['/main']);
  }
}
