import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faClock, faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import { NewTodoComponent } from './page/new-todo/new-todo.component';
import { NotFoundPageComponent } from './page/not-found-page/not-found-page.component';
import { HomeComponent } from './page/home/home.component';


@NgModule({
  declarations: [
    AppComponent,
    NewTodoComponent,
    NotFoundPageComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor() {
    library.add(faClock);
    library.add(faCheckCircle);
  }
}
