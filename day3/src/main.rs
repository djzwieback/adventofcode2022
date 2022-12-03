use std::collections::HashSet;
use std::fs;
use std::str::Chars;
use itertools::Itertools;

struct Rucksack {
    first: HashSet<char>,
    second: HashSet<char>,
}

fn main() {
    let contents = fs::read_to_string("/Users/risc/dev/adventofcode/2022/day3/input.txt")
        .expect("Should have been able to read the file");
    let rucksacks: Vec<Rucksack> =
        contents.lines()
            .map(|line| line.split_at(line.len() / 2))
            .map(|map| Rucksack {
                first: HashSet::from_iter((map.0).chars()),
                second: HashSet::from_iter((map.1).chars()),
            })
            .collect();
    let mut result: u32 = 0;

    let mut container: Vec<char> = Vec::new();
    for (index, rucksack) in rucksacks.iter().enumerate() {
        println!("First: {}", rucksack.first.iter().join(""));
        for character in rucksack.first.iter() {
            if rucksack.second.contains(character) {
                result += get_priority(character)
            }
        }
    }
    println!("End result is {}", result)
}

fn get_priority(character: &char) -> u32 {
    let offset = if character.is_lowercase() { 96 } else { 38 };
    *character as u32 - offset
}
