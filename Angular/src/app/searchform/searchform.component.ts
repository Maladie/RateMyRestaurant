import { Component, OnInit } from '@angular/core';
import {SearchInRadius} from "../SearchInRadius";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-searchform',
  templateUrl: './searchform.component.html',
  styleUrls: ['./searchform.component.css']
})
export class SearchformComponent implements OnInit {
 model;
 submitted;
  constructor() {
    this.model = new SearchInRadius(0, 0, 0, 'bar');

    this.submitted = false;



  }
  onSubmit(f: NgForm) {
    this.submitted = true; }

  ngOnInit() {
  }

}
