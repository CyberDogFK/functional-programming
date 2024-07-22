use std::collections::HashMap;

fn main() {
    let mut mem = Memoization{ memoization_cache: HashMap::new() };
    println!("{}", mem.compute_expensive_square(&4));
    println!("{}", mem.compute_expensive_square(&4));
}

struct Memoization {
    memoization_cache: HashMap<i32, i32>
}

impl Memoization {
    fn do_compute_expensive_square(input: &i32) -> i32 {
        println!("Computing square");
        return input * input;
    }

    pub fn compute_expensive_square(&mut self, input: &i32) -> &i32 {
        if !self.memoization_cache.contains_key(input) {
            self.memoization_cache.insert(input.clone(),
            Self::do_compute_expensive_square(input));
        }
        return self.memoization_cache.get(input).unwrap()
    }
}
