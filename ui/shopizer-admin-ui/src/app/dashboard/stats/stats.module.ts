import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StatsComponent } from './stats.component';

@NgModule({
    imports: [RouterModule],
    declarations: [StatsComponent],
    exports: [StatsComponent]
})

export class StatsModule { }
