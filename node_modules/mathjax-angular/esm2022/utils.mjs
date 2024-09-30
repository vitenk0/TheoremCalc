//
import { isMathjaxRegExp } from './models';
//
export const isMathjax = (expression) => !!expression?.match(isMathjaxRegExp);
//
/**
 * find and return mathjax string from input
 * @param expressions
 * @returns mathjax string
 */
export const getMathjaxContent = (expressions) => {
    if (!expressions)
        return '';
    else if ('string' === typeof expressions)
        return expressions;
    else
        return expressions.latex ?? expressions.mathml ?? '';
};
/**
 * used to fix few issues with mathjax string in angular
 * @param  {string} jax mathjax string
 * @returns {string} fixed string
 */
export const fixMathjaxBugs = (jax) => {
    return (jax
        //line break error
        .replace(/<br \/>/gi, '<br/> ')
        //automatic breakline
        .replace(/[$]([\s\S]+?)[$]/gi, (m, p, o, s) => {
        //return /s/gi.test(p)
        return p.includes('\\\\') && !p.includes('\\begin')
            ? `$\\begin{align*}${p}\\end{align*}$`
            : `$${p}$`;
    }));
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoidXRpbHMuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi8uLi9wcm9qZWN0cy9tYXRoamF4LWxpYi9zcmMvdXRpbHMudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsRUFBRTtBQUVGLE9BQU8sRUFBRSxlQUFlLEVBQWtCLE1BQU0sVUFBVSxDQUFDO0FBRTNELEVBQUU7QUFDRixNQUFNLENBQUMsTUFBTSxTQUFTLEdBQUcsQ0FBQyxVQUFrQixFQUFXLEVBQUUsQ0FDdkQsQ0FBQyxDQUFDLFVBQVUsRUFBRSxLQUFLLENBQUMsZUFBZSxDQUFDLENBQUM7QUFFdkMsRUFBRTtBQUNGOzs7O0dBSUc7QUFDSCxNQUFNLENBQUMsTUFBTSxpQkFBaUIsR0FBRyxDQUMvQixXQUFvQyxFQUM1QixFQUFFO0lBQ1YsSUFBSSxDQUFDLFdBQVc7UUFBRSxPQUFPLEVBQUUsQ0FBQztTQUN2QixJQUFJLFFBQVEsS0FBSyxPQUFPLFdBQVc7UUFBRSxPQUFPLFdBQXFCLENBQUM7O1FBQ2xFLE9BQU8sV0FBVyxDQUFDLEtBQUssSUFBSSxXQUFXLENBQUMsTUFBTSxJQUFJLEVBQUUsQ0FBQztBQUM1RCxDQUFDLENBQUM7QUFDRjs7OztHQUlHO0FBQ0gsTUFBTSxDQUFDLE1BQU0sY0FBYyxHQUFHLENBQUMsR0FBVyxFQUFVLEVBQUU7SUFDcEQsT0FBTyxDQUNMLEdBQUc7UUFDRCxrQkFBa0I7U0FDakIsT0FBTyxDQUFDLFdBQVcsRUFBRSxRQUFRLENBQUM7UUFDL0IscUJBQXFCO1NBQ3BCLE9BQU8sQ0FBQyxvQkFBb0IsRUFBRSxDQUFDLENBQUMsRUFBRSxDQUFTLEVBQUUsQ0FBQyxFQUFFLENBQUMsRUFBRSxFQUFFO1FBQ3BELHNCQUFzQjtRQUN0QixPQUFPLENBQUMsQ0FBQyxRQUFRLENBQUMsTUFBTSxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsUUFBUSxDQUFDLFNBQVMsQ0FBQztZQUNqRCxDQUFDLENBQUMsbUJBQW1CLENBQUMsZ0JBQWdCO1lBQ3RDLENBQUMsQ0FBQyxJQUFJLENBQUMsR0FBRyxDQUFDO0lBQ2YsQ0FBQyxDQUFDLENBQ0wsQ0FBQztBQUNKLENBQUMsQ0FBQyIsInNvdXJjZXNDb250ZW50IjpbIi8vXG5cbmltcG9ydCB7IGlzTWF0aGpheFJlZ0V4cCwgTWF0aGpheENvbnRlbnQgfSBmcm9tICcuL21vZGVscyc7XG5cbi8vXG5leHBvcnQgY29uc3QgaXNNYXRoamF4ID0gKGV4cHJlc3Npb246IHN0cmluZyk6IGJvb2xlYW4gPT5cbiAgISFleHByZXNzaW9uPy5tYXRjaChpc01hdGhqYXhSZWdFeHApO1xuXG4vL1xuLyoqXG4gKiBmaW5kIGFuZCByZXR1cm4gbWF0aGpheCBzdHJpbmcgZnJvbSBpbnB1dFxuICogQHBhcmFtIGV4cHJlc3Npb25zXG4gKiBAcmV0dXJucyBtYXRoamF4IHN0cmluZ1xuICovXG5leHBvcnQgY29uc3QgZ2V0TWF0aGpheENvbnRlbnQgPSAoXG4gIGV4cHJlc3Npb25zOiBNYXRoamF4Q29udGVudCB8IHN0cmluZ1xuKTogc3RyaW5nID0+IHtcbiAgaWYgKCFleHByZXNzaW9ucykgcmV0dXJuICcnO1xuICBlbHNlIGlmICgnc3RyaW5nJyA9PT0gdHlwZW9mIGV4cHJlc3Npb25zKSByZXR1cm4gZXhwcmVzc2lvbnMgYXMgc3RyaW5nO1xuICBlbHNlIHJldHVybiBleHByZXNzaW9ucy5sYXRleCA/PyBleHByZXNzaW9ucy5tYXRobWwgPz8gJyc7XG59O1xuLyoqXG4gKiB1c2VkIHRvIGZpeCBmZXcgaXNzdWVzIHdpdGggbWF0aGpheCBzdHJpbmcgaW4gYW5ndWxhclxuICogQHBhcmFtICB7c3RyaW5nfSBqYXggbWF0aGpheCBzdHJpbmdcbiAqIEByZXR1cm5zIHtzdHJpbmd9IGZpeGVkIHN0cmluZ1xuICovXG5leHBvcnQgY29uc3QgZml4TWF0aGpheEJ1Z3MgPSAoamF4OiBzdHJpbmcpOiBzdHJpbmcgPT4ge1xuICByZXR1cm4gKFxuICAgIGpheFxuICAgICAgLy9saW5lIGJyZWFrIGVycm9yXG4gICAgICAucmVwbGFjZSgvPGJyIFxcLz4vZ2ksICc8YnIvPiAnKVxuICAgICAgLy9hdXRvbWF0aWMgYnJlYWtsaW5lXG4gICAgICAucmVwbGFjZSgvWyRdKFtcXHNcXFNdKz8pWyRdL2dpLCAobSwgcDogc3RyaW5nLCBvLCBzKSA9PiB7XG4gICAgICAgIC8vcmV0dXJuIC9zL2dpLnRlc3QocClcbiAgICAgICAgcmV0dXJuIHAuaW5jbHVkZXMoJ1xcXFxcXFxcJykgJiYgIXAuaW5jbHVkZXMoJ1xcXFxiZWdpbicpXG4gICAgICAgICAgPyBgJFxcXFxiZWdpbnthbGlnbip9JHtwfVxcXFxlbmR7YWxpZ24qfSRgXG4gICAgICAgICAgOiBgJCR7cH0kYDtcbiAgICAgIH0pXG4gICk7XG59O1xuIl19