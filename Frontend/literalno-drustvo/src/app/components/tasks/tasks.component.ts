import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  tasks: any[] = [];

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getTasks().subscribe(
      (res: any) => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log(err);
      }
    )

    this.userService.tasksChange.subscribe(
      res => 
      this.userService.getTasks().subscribe(
        (res: any) => {
          console.log(res);
          this.tasks = res;
        },
        err => {
          console.log(err);
        }
      )
    );
  }
}
