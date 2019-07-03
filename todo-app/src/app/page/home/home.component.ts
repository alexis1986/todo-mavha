import { BackendService } from './../../service/backend.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  todos;
  id: number;
  description: string;
  status: string;

  constructor(private service: BackendService) {}

  ngOnInit() {
    this.findAll();
  }

  resolve(id: number) {
    this.service
          .resolve(id)
          .subscribe(
            () => {
              this.findAll();
            }
          );
  }

  search() {
    this.service
          .findBy(this.id, this.description, this.status)
          .subscribe(
            data => {
              this.todos = data;
            }
          );
  }

  private findAll() {
    this.service
      .all()
      .subscribe(data => {
        this.todos = data;
      });
  }

}
