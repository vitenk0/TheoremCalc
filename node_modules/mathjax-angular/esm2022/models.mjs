/**
 * will help to check if expression is valid majax or not
 */
export const isMathjaxRegExp = /(?:\$|\\\(|\\\[|\\begin\{.*?})/;
//export const isMathJax = /(?:(?:^|[-+_*/])(?:\s*-?\d+(\.\d+)?(?:[eE][+-]?\d+)?\s*))+$/;
//
export const mathjax_url = 'https://cdn.jsdelivr.net/npm/mathjax@3.2.2/es5/startup.js';
/**
 * config - http://docs.mathjax.org/en/latest/web/configuration.html#configuring-and-loading-mathjax
 */
export const MathjaxDefaultConfig = {
    loader: {
        load: ['output/svg', '[tex]/require', '[tex]/ams'],
    },
    tex: {
        inlineMath: [['$', '$']],
        //displayMath: [['$$', '$$']],
        packages: ['base', 'require', 'ams'],
    },
    svg: { fontCache: 'global' },
};
/**
 * config - http://docs.mathjax.org/en/latest/web/configuration.html#configuring-and-loading-mathjax
 * src - cdn url to js
 */
export class RootMathjaxConfig {
}
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoibW9kZWxzLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vcHJvamVjdHMvbWF0aGpheC1saWIvc3JjL21vZGVscy50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFRQTs7R0FFRztBQUNILE1BQU0sQ0FBQyxNQUFNLGVBQWUsR0FBRyxnQ0FBZ0MsQ0FBQztBQUNoRSx5RkFBeUY7QUFDekYsRUFBRTtBQUNGLE1BQU0sQ0FBQyxNQUFNLFdBQVcsR0FDdEIsMkRBQTJELENBQUM7QUFFOUQ7O0dBRUc7QUFDSCxNQUFNLENBQUMsTUFBTSxvQkFBb0IsR0FBRztJQUNsQyxNQUFNLEVBQUU7UUFDTixJQUFJLEVBQUUsQ0FBQyxZQUFZLEVBQUUsZUFBZSxFQUFFLFdBQVcsQ0FBQztLQUNuRDtJQUNELEdBQUcsRUFBRTtRQUNILFVBQVUsRUFBRSxDQUFDLENBQUMsR0FBRyxFQUFFLEdBQUcsQ0FBQyxDQUFDO1FBQ3hCLDhCQUE4QjtRQUM5QixRQUFRLEVBQUUsQ0FBQyxNQUFNLEVBQUUsU0FBUyxFQUFFLEtBQUssQ0FBQztLQUNyQztJQUNELEdBQUcsRUFBRSxFQUFFLFNBQVMsRUFBRSxRQUFRLEVBQUU7Q0FDN0IsQ0FBQztBQUNGOzs7R0FHRztBQUNILE1BQU0sT0FBTyxpQkFBaUI7Q0FHN0IiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIG1vcmUgY29tcGxleCBtYXRqYXggY29udGVudCB3aWNoIHN1cHBvciBsYXRleCBhbmQgbWF0aG1sIHNlcGVyYXRlbHlcbiAqIHdpbGwgYmUgY29uc2lkZXJlZCBmb3IgZnV0dXJlIGRldmVsb3BtZW50XG4gKi9cbmV4cG9ydCBpbnRlcmZhY2UgTWF0aGpheENvbnRlbnQge1xuICBsYXRleD86IHN0cmluZztcbiAgbWF0aG1sPzogc3RyaW5nO1xufVxuLyoqXG4gKiB3aWxsIGhlbHAgdG8gY2hlY2sgaWYgZXhwcmVzc2lvbiBpcyB2YWxpZCBtYWpheCBvciBub3RcbiAqL1xuZXhwb3J0IGNvbnN0IGlzTWF0aGpheFJlZ0V4cCA9IC8oPzpcXCR8XFxcXFxcKHxcXFxcXFxbfFxcXFxiZWdpblxcey4qP30pLztcbi8vZXhwb3J0IGNvbnN0IGlzTWF0aEpheCA9IC8oPzooPzpefFstK18qL10pKD86XFxzKi0/XFxkKyhcXC5cXGQrKT8oPzpbZUVdWystXT9cXGQrKT9cXHMqKSkrJC87XG4vL1xuZXhwb3J0IGNvbnN0IG1hdGhqYXhfdXJsID1cbiAgJ2h0dHBzOi8vY2RuLmpzZGVsaXZyLm5ldC9ucG0vbWF0aGpheEAzLjIuMi9lczUvc3RhcnR1cC5qcyc7XG5cbi8qKlxuICogY29uZmlnIC0gaHR0cDovL2RvY3MubWF0aGpheC5vcmcvZW4vbGF0ZXN0L3dlYi9jb25maWd1cmF0aW9uLmh0bWwjY29uZmlndXJpbmctYW5kLWxvYWRpbmctbWF0aGpheFxuICovXG5leHBvcnQgY29uc3QgTWF0aGpheERlZmF1bHRDb25maWcgPSB7XG4gIGxvYWRlcjoge1xuICAgIGxvYWQ6IFsnb3V0cHV0L3N2ZycsICdbdGV4XS9yZXF1aXJlJywgJ1t0ZXhdL2FtcyddLFxuICB9LFxuICB0ZXg6IHtcbiAgICBpbmxpbmVNYXRoOiBbWyckJywgJyQnXV0sXG4gICAgLy9kaXNwbGF5TWF0aDogW1snJCQnLCAnJCQnXV0sXG4gICAgcGFja2FnZXM6IFsnYmFzZScsICdyZXF1aXJlJywgJ2FtcyddLFxuICB9LFxuICBzdmc6IHsgZm9udENhY2hlOiAnZ2xvYmFsJyB9LFxufTtcbi8qKlxuICogY29uZmlnIC0gaHR0cDovL2RvY3MubWF0aGpheC5vcmcvZW4vbGF0ZXN0L3dlYi9jb25maWd1cmF0aW9uLmh0bWwjY29uZmlndXJpbmctYW5kLWxvYWRpbmctbWF0aGpheFxuICogc3JjIC0gY2RuIHVybCB0byBqc1xuICovXG5leHBvcnQgY2xhc3MgUm9vdE1hdGhqYXhDb25maWcge1xuICBjb25maWc/OiB7IFtuYW1lOiBzdHJpbmddOiBhbnkgfTtcbiAgc3JjPzogc3RyaW5nO1xufVxuIl19