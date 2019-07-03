import { BackendService } from './../../service/backend.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-todo',
  templateUrl: './new-todo.component.html',
  styleUrls: ['./new-todo.component.scss']
})
export class NewTodoComponent implements OnInit {
  todoForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private service: BackendService, private router: Router) { }

  ngOnInit() {
    this.todoForm = this.formBuilder.group({
      description: ['', Validators.required],
      image: ['', Validators.required]
    });
  }

  onImageSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.todoForm.get('image').setValue(file);
    }
  }

  save() {
    const formData = new FormData();
    formData.append('description', this.todoForm.get('description').value);
    formData.append('image', this.todoForm.get('image').value);
    this.service
        .save(formData)
        .subscribe(
          () => {
            this.router.navigate(['/home']);
          }
        );
  }
}
