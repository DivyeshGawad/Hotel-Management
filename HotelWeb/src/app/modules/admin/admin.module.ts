import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostRoomComponent } from './components/post-room/post-room.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DemoNgZorroAntdModule } from 'src/app/DemoNgZorroAntdModule';
import { UpdateRoomComponent } from './components/update-room/update-room.component';
import { ReservationsComponent } from './components/reservations/reservations.component';
import { NzIconModule, NZ_ICONS } from 'ng-zorro-antd/icon';
import { CheckCircleTwoTone, CloseCircleTwoTone, SyncOutline } from '@ant-design/icons-angular/icons';

@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    PostRoomComponent,
    UpdateRoomComponent,
    ReservationsComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    HttpClientModule,
    FormsModule,
    DemoNgZorroAntdModule,
    ReactiveFormsModule,
    NzIconModule,
  ],
  providers: [
    { provide: NZ_ICONS, useValue: [ CheckCircleTwoTone, CloseCircleTwoTone, SyncOutline ] }
  ]
})
export class AdminModule { }
