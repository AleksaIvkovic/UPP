import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  tasks: any[] = [];

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userService.getTasks().subscribe(
      (res: any) => {
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
          this.tasks = res;
        },
        err => {
          console.log(err);
        }
      )
    );
  }

  CheckIfTaskIsOpen(){
    return this.router.url.includes('/tasks/')
  }
}
