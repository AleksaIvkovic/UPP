import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { UserService} from '../../services/user.service'

@Component({
  selector: 'app-email-confirmation',
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css']
})
export class EmailConfirmationComponent implements OnInit {
  message = "";


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private readerService: UserService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.readerService.confirmEmail(params['token'],params['processId']).subscribe(
          res => {
            this.message = "Confirmation was successful";
          },
          err => {
            this.message = "Confirmation was NOT successful";
          }
        )
      }
    )
  }

}
