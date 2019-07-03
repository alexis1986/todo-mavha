import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private client: HttpClient) { }

  all() {
    return this.client.get('/backend/todo');
  }

  findBy(id: number, description: string, status: string) {
    const params = new HttpParams()
                        .set('id', id ? String(id) : '')
                        .set('description', description ? description : '')
                        .set('status', (status && status !== '-1') ? status : '');
    return this.client.get('/backend/todo', {params});
  }

  save(formData: FormData) {
    return this.client.post('/backend/todo', formData);
  }

  resolve(id: number) {
    return this.client.patch('/backend/todo/' + id, {});
  }
}
