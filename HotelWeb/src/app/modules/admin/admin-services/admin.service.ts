import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/auth/component/storaage/user-storage.service';

const BASE_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  postRoomDetails(roomDto: any): Observable<any>{
    return this.http.post(BASE_URL + `api/admin/room`, roomDto,{
      headers: this.createAuthorizationHeader(),
    });
  }

  getRooms(pageNumber: number): Observable<any>{
    return this.http.get(BASE_URL + `api/admin/rooms/${pageNumber}`,{
      headers: this.createAuthorizationHeader(),
    });
  }

  getRoomById(id: number): Observable<any>{
    return this.http.get(BASE_URL + `api/admin/room/${id}`,{
      headers: this.createAuthorizationHeader(),
    });
  }

  updateRoomDetails(id: number,roomDto: any): Observable<any>{
    return this.http.post(BASE_URL + `api/admin/room/${id}`, roomDto,{
      headers: this.createAuthorizationHeader(),
    });
  }

  deleteRoom(roomId: number): Observable<any>{
    return this.http.delete(BASE_URL + `api/admin/room/${roomId}`,{
      headers: this.createAuthorizationHeader(),
    })
  }

  getReservations(pageNumber: number): Observable<any> {
    return this.http.get(BASE_URL + `api/admin/reservations/${pageNumber}`, {
      headers: this.createAuthorizationHeader(),
    });
}

changeReservationStatus(reservationId: number, status:string): Observable<any> {
  return this.http.get(BASE_URL + `api/admin/reservation/${reservationId}/${status}`, {
    headers: this.createAuthorizationHeader(),
  });
}

  createAuthorizationHeader(){
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set(
      'Authorization',
      'Bearer '+UserStorageService.getToken(),
    );
  }
}
