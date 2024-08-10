fn main() {
    let compute_hourly = |hours: i32, rate: f32| hours as f32 * rate;
    let compute_salary = |rate: f32| rate * 40.0;
    let compute_sales = |rate: f32, commission: f32| rate * 40.0 + commission;

    let hourly_pay = compute_hourly(35, 12.75);
    let salary_pay = compute_salary(25.35);
    let sales_pay = compute_sales(8.75, 2500.0);

    let hourly = false;
    let total = if hourly {
        hourly_pay
    } else {
        salary_pay + sales_pay
    };
    println!("{}", total)
}
