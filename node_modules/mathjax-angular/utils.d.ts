import { MathjaxContent } from './models';
export declare const isMathjax: (expression: string) => boolean;
/**
 * find and return mathjax string from input
 * @param expressions
 * @returns mathjax string
 */
export declare const getMathjaxContent: (expressions: MathjaxContent | string) => string;
/**
 * used to fix few issues with mathjax string in angular
 * @param  {string} jax mathjax string
 * @returns {string} fixed string
 */
export declare const fixMathjaxBugs: (jax: string) => string;
