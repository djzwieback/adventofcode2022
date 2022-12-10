use std::fs;
use std::ops::Add;

use regex::Regex;

struct Operation {
    name: String,
    value: i32,
}

fn main() {
    let contents = fs::read_to_string("/Users/risc/dev/adventofcode/2022/day10/input.txt")
        .expect("Should have been able to read the file");

    let mut sum = 0;
    let mut register = 1;
    let mut cycle = 0;
    let mut ctr = 0;
    contents.lines().filter(|x| { !x.is_empty() }).for_each(|x| {
        // println!("Line is {}", x);
        if x == "noop" {
            // do nothing besides increasing cycle
            if cycle % 40 == 0 {
                ctr = 0;
                println!();
            }
            print!("{}", get_pixel(register, ctr));
            cycle += 1;
            ctr += 1;
            sum += get_singal_strength(register, cycle);
        } else {
            let mut split = x.split(" ");
            let operation = Operation {
                name: split.next().unwrap().parse().unwrap(),
                value: split.next().unwrap().parse().unwrap(),
            };
            if cycle % 40 == 0 {
                ctr = 0;
                println!();
            }
            print!("{}", get_pixel(register, ctr));
            cycle += 1;
            ctr += 1;
            sum += get_singal_strength(register, cycle);
            if cycle % 40 == 0 {
                ctr = 0;
                println!();
            }
            print!("{}", get_pixel(register, ctr));
            cycle += 1;
            ctr += 1;
            sum += get_singal_strength(register, cycle);
            register += operation.value;
        }
    });

    println!();
    println!("Sum is {}", sum)
}

fn get_singal_strength(register: i32, cycle: i32) -> i32 {
    if (cycle - 20) % 40 == 0 {
        return register * cycle;
    }
    return 0;
}

fn get_pixel(register: i32, cycle: i32) -> &'static str {
    if cycle >= register - 1 && cycle <= register + 1 {
        return "#";
    } else {
        return ".";
    }
}

