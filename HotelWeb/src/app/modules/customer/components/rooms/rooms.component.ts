import { Component } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { CustomerService } from '../../service/customer.service';
import { UserStorageService } from 'src/app/auth/component/storaage/user-storage.service';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent {

  currentPage: number = 1;
  rooms: any[] = [];
  total: number = 0;
  loading: boolean = false;

  isVisibleMiddle: boolean = false;
  date: Date[] = [];
  checkInDate!: Date;
  checkOutDate!: Date;
  id!: number;

  constructor(
    private customerService: CustomerService,
    private message: NzMessageService,
    private modalService: NzModalService,
  ) {
    this.getRooms();
  }

  // Fetch rooms based on the current page
  getRooms(): void {
    this.loading = true;
    this.customerService.getRooms(this.currentPage - 1).subscribe(res => {
      console.log(res);
      this.rooms = res.roomDtoList;
      this.total = res.totalPages * 1;
      this.loading = false;
    }, error => {
      this.message.error('Failed to load rooms');
      this.loading = false;
    });
  }

  // Handle pagination change
  pageIndexChange(value: number): void {
    this.currentPage = value;
    this.getRooms();
  }

  // Handle date picker change
  onChange(result: Date[]): void {
    if (result.length === 2) {
      this.checkInDate = result[0];
      this.checkOutDate = result[1];
    }
  }

  // Handle modal cancellation
  handleCancelMiddle(): void {
    this.isVisibleMiddle = false;
  }

  // Handle modal OK (confirm booking)
  handleOkMiddle(): void {
    const obj = {
      userId: UserStorageService.getUserId(),  // Use instance method if not static
      roomId: this.id,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate,
    };

    this.customerService.bookRoom(obj).subscribe(res => {
      this.message.success("Request Submitted for Approval", { nzDuration: 5000 });
      this.isVisibleMiddle = false;
    }, error => {
      this.message.error(`${error.error}`, { nzDuration: 5000 });
    });
  }

  // Show modal for booking a room
  showModalMiddle(id: number): void {
    this.id = id;
    this.isVisibleMiddle = true;
  }
}
