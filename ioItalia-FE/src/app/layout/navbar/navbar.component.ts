import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Url_paths } from 'src/app/util/contants';



@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css', '../../app.component.css']
})
export class NavbarComponent implements OnInit{
  mobileQuery: MediaQueryList;
  @ViewChild('snav', {static: false}) sidenav: MatSidenav;
  isExpanded = true;
  showSubmenuServizi: boolean = false;
  isShowing = false;

  
  
  servizi: string = Url_paths.SERVIZI;
 

  private _mobileQueryListener: () => void;


  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 959px)');
    console.log(this.mobileQuery);
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
  }

  
  closeSidenavOnClick() {
    if (this.mobileQuery.matches) {
      this.sidenav.close();
    }

  }


  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }


}
