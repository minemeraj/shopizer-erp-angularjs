import { Component } from '@angular/core';
import { User } from '../../user/index';

@Component({
  selector: 'sidebar-cmp',
  templateUrl: 'sidebar.html'
})

export class SidebarComponent {
  isActive = false;
  showMenu: string = '';
  currentUser: User;
  
  constructor() {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }
  
  eventCalled() {
    this.isActive = !this.isActive;
  }
  addExpandClass(element: any) {
    if (element === this.showMenu) {
      this.showMenu = '0';
    } else {
      this.showMenu = element;
    }
  }
}
