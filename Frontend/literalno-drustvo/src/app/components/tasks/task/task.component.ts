import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  @Input() task: any;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  getTask() {
    this.router.navigate([this.task.id, this.task.name], {relativeTo: this.activeRoute});
  }
}
