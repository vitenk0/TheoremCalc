export interface apiResponse<T> {
    data: T | null;
    error: string | null;
}