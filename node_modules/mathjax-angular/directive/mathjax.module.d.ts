import { ModuleWithProviders } from '@angular/core';
import { RootMathjaxConfig } from '../models';
import * as i0 from "@angular/core";
import * as i1 from "./mathjax.directive";
export declare class MathjaxModule {
    private moduleConfig;
    constructor(moduleConfig: RootMathjaxConfig);
    private addConfigToDocument;
    private addMatjaxToDocument;
    static forRoot(config?: RootMathjaxConfig): ModuleWithProviders<MathjaxModule>;
    static forChild(): ModuleWithProviders<MathjaxModule>;
    static ɵfac: i0.ɵɵFactoryDeclaration<MathjaxModule, never>;
    static ɵmod: i0.ɵɵNgModuleDeclaration<MathjaxModule, [typeof i1.MathjaxDirective], never, [typeof i1.MathjaxDirective]>;
    static ɵinj: i0.ɵɵInjectorDeclaration<MathjaxModule>;
}
