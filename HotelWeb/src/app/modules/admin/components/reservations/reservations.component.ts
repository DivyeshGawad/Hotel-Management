import { Component } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.scss']
})
export class ReservationsComponent {

  currentPage: number = 1;
  total: number = 0;
  reservations: any[] = [];

  constructor(
    private adminService: AdminService,
    private message: NzMessageService,
  ) { 
    this.getReservations();
  }

  getReservations(){
    this.adminService.getReservations(this.currentPage - 1).subscribe(res =>{
      console.log(res);
      this.reservations = res.reservationDtoList;
      this.total = res.totalPages * 5;
    });
  }

  pageIndexChange(value: number){
    this.currentPage = value;
    this.getReservations();
  }

  changeReservationStatus(reservationId: number, status: string){
    this.adminService.changeReservationStatus(reservationId, status).subscribe(res =>{
      this.message.success("Reservation Status Updated Successfully",{nzDuration: 5000});
      this.getReservations();
    }, error =>{
      this.message.error(`${error.error}`,{nzDuration: 5000});
    })
  }
}
