import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/auth/component/storaage/user-storage.service';

const BASE_URL = `http://localhost:8080/`;

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getRooms(pageNumber: number): Observable<any>{
    return this.http.get(BASE_URL + `api/customer/rooms/${pageNumber}`,{
      headers: this.createAuthorizationHeader(),
    });
  }

  getMyBookings(pageNumber: number): Observable<any>{
    const userId = UserStorageService.getUserId();
    return this.http.get(BASE_URL + `api/customer/bookings/${userId}/${pageNumber}`,{
      headers: this.createAuthorizationHeader(),
    });
  }

  bookRoom(bookingDto: any): Observable<any>{
    return this.http.post(BASE_URL + `api/customer/book`, bookingDto,{
      headers: this.createAuthorizationHeader(),
    })
  }

  createAuthorizationHeader(){
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set(
      'Authorization',
      'Bearer '+UserStorageService.getToken(),
    );
  }
}
