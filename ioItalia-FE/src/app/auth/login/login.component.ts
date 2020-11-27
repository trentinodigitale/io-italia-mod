import { Component, OnInit } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../../app.component.css']
})
export class LoginComponent implements OnInit {


  constructor(private oauthService: OAuthService) { }

  ngOnInit() {

  }

  public login() {
    this.oauthService.initLoginFlow();
  }

}
