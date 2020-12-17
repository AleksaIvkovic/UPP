import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StarterService } from '../../services/starter.service'

@Component({
  selector: 'app-starter',
  templateUrl: './starter.component.html',
  styleUrls: ['./starter.component.css']
})
export class StarterComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private starterService: StarterService
  ) { }

  ngOnInit(): void {
  }

  StartRegisterProcess(){
    this.starterService.getRegisterForm().subscribe(
      (res) => {
        sessionStorage.setItem("processId", res.processId );
        this.router.navigate(['../','register-reader']);
      },
      (err) => {

      }
    )
  }

}
