import {Component, OnInit} from '@angular/core';
import {OAuthService} from 'angular-oauth2-oidc';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../../app.component.css']
})
export class LoginComponent implements OnInit {
  iotlogo: string;
  tellogo: string;

  constructor(private oauthService: OAuthService) {
    this.iotlogo = 'assets/image/io-it-logo-blue.svg';
    this.tellogo = 'assets/image/telegram.png';
  }

  ngOnInit() {


  }

  public login() {
    this.oauthService.initLoginFlow();
  }

}
