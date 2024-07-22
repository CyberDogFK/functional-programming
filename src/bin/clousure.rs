fn main() {
    let some_vec = vec!["1", "2", "3"];
    let mut b = "aaa";
    some_vec
        .into_iter()
        .for_each(|t| {
            b = "5";
            println!("{}", b)
        }
    );
}
