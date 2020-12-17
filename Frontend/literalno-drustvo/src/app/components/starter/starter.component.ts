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

  StartReaderRegistrationProcess(){
    this.starterService.startReaderRegistration().subscribe(
      (res) => {
        sessionStorage.setItem("processId", res.processId );
        this.router.navigate(['../','register-reader']);
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
        this.router.navigate(['../','register-writer']);
      },
      (err) => {
        console.log(err);
      }
    )
  }

}
