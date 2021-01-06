import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { BlockScrollStrategy } from '@angular/cdk/overlay';
import { StarterService } from 'src/app/services/starter.service';
import { SysUser } from 'src/app/models/sysUser.model';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

    constructor(
      private breakpointObserver: BreakpointObserver,
      public router: Router,
      public activeRoute: ActivatedRoute,
      private starterService: StarterService) {}

    
    submitNewBook(){
      if (this.router.url.includes('submit-new-book') || this.router.url.includes('Book%20synopsis')) {
        return;
      }

      this.starterService.startBookPublishing().subscribe(
        (res) => {
          sessionStorage.setItem("processId", res.processId );
          this.router.navigate(['submit-new-book'], {relativeTo : this.activeRoute});
        },
        (err) => {
          console.log(err);
        }
      );
    }

    fileAnAppeal(){
      if (this.router.url.includes('file-an-appeal') || this.router.url.includes('File%20an%20appeal')) {
        return;
      }

      this.starterService.startWriterProcess("Plagiarism").subscribe(
        (res) => {
          sessionStorage.setItem("processId", res.processId );
          this.router.navigate(['file-an-appeal'], {relativeTo : this.activeRoute});
        },
        (err) => {
          console.log(err);
        }
      );
    }

    checkIfActiveWriter(){
      let user = <SysUser>JSON.parse(sessionStorage.getItem('loggedInUser'));
      if(user == null){
        return false;
      }
      return (user.authority == 'WRITER' && user.isActive);
    }

    checkIfLoggedin(){
      let user = <SysUser>JSON.parse(sessionStorage.getItem('loggedInUser'));
      return user != null;
    }

}

