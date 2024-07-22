use std::str::FromStr;

 fn main() {
    let list = vec!["AAA", "BBB", "CCC"];
     for element in &list {
         println!("{}", element.to_lowercase())
     }
     list.iter().for_each(|s| {
         println!("{}",
         process_string(str::to_lowercase, s))
     });
     list.iter().for_each(|s| {
         println!("{}",
         process_string(str::to_uppercase, s))
     });

    let number_string = vec!["12", "34", "82"];
    let mut numbers: Vec<i32> = vec![];
    let mut double_numbers: Vec<i32> = vec![];

    for num in &number_string {
        numbers.push(i32::from_str(num).unwrap())
    }

    numbers.clear();
    number_string
        .into_iter()
        .for_each(|s| numbers.push(i32::from_str(s).unwrap()))
}

fn process_string(operation: fn(&str) -> String, target: &str) -> String {
    return operation(target);
}
