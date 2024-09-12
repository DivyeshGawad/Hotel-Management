import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerComponent } from './customer.component';
import { RoomsComponent } from './components/rooms/rooms.component';
import { DemoNgZorroAntdModule } from 'src/app/DemoNgZorroAntdModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { ViewBookingsComponent } from './components/view-bookings/view-bookings.component';
import { NZ_ICONS, NzIconModule } from 'ng-zorro-antd/icon';
import { CheckCircleTwoTone, CloseCircleTwoTone, SyncOutline } from '@ant-design/icons-angular/icons';


@NgModule({
  declarations: [
    CustomerComponent,
    RoomsComponent,
    ViewBookingsComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    DemoNgZorroAntdModule,
    ReactiveFormsModule,
    FormsModule,
    NzModalModule,
    NzDatePickerModule,
    NzIconModule,
  ],
  providers: [
    { provide: NZ_ICONS, useValue: [ CheckCircleTwoTone, CloseCircleTwoTone, SyncOutline ] }
  ]
})
export class CustomerModule { }
