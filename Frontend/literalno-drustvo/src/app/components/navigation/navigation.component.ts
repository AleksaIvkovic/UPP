import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { BlockScrollStrategy } from '@angular/cdk/overlay';
import { StarterService } from 'src/app/services/starter.service';

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

}

