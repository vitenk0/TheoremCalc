/**
 * more complex matjax content wich suppor latex and mathml seperately
 * will be considered for future development
 */
export interface MathjaxContent {
    latex?: string;
    mathml?: string;
}
/**
 * will help to check if expression is valid majax or not
 */
export declare const isMathjaxRegExp: RegExp;
export declare const mathjax_url = "https://cdn.jsdelivr.net/npm/mathjax@3.2.2/es5/startup.js";
/**
 * config - http://docs.mathjax.org/en/latest/web/configuration.html#configuring-and-loading-mathjax
 */
export declare const MathjaxDefaultConfig: {
    loader: {
        load: string[];
    };
    tex: {
        inlineMath: string[][];
        packages: string[];
    };
    svg: {
        fontCache: string;
    };
};
/**
 * config - http://docs.mathjax.org/en/latest/web/configuration.html#configuring-and-loading-mathjax
 * src - cdn url to js
 */
export declare class RootMathjaxConfig {
    config?: {
        [name: string]: any;
    };
    src?: string;
}
