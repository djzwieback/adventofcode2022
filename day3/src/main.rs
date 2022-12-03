use std::collections::HashSet;
use std::fs;
use itertools::Itertools;
use regex::Regex;

struct Rucksack {
    first: HashSet<char>,
    second: HashSet<char>,
}

fn main() {
    let contents = fs::read_to_string("/Users/risc/dev/adventofcode/2022/day3/input.txt")
        .expect("Should have been able to read the file");

    riddle_one(contents.clone());
    riddle_two(contents)
}

fn riddle_one(contents: String) {
    let rucksacks: Vec<Rucksack> =
        contents.lines()
            .map(|line| line.split_at(line.len() / 2))
            .map(|map| Rucksack {
                first: HashSet::from_iter((map.0).chars()),
                second: HashSet::from_iter((map.1).chars()),
            })
            .collect();
    let mut result: u32 = 0;

    for rucksack in rucksacks.iter() {
        for character in rucksack.first.iter() {
            if rucksack.second.contains(character) {
                result += get_priority(character)
            }
        }
    }
    println!("First riddle result is {}", result)
}

//imo prettier solution using regex
fn riddle_two(contents: String) {
    let mut container: Vec<String> = Vec::new();
    let lines: Vec<&str> = contents.lines().collect();
    for (index, line) in lines.iter().enumerate() {
        if index % 3 == 1 || index % 3 == 2 {
            continue;
        }
        let regex = build_regex(&line);
        let caps = regex.find_iter(lines[index + 1]).map(|hit| hit.as_str()).join("");
        // let caps = regex.captures(lines[index + 1]).unwrap().iter().map(|m| m.map_or("", |a| a.as_str())).join("");
        let regex = build_regex(&caps);
        let caps = regex.find_iter(lines[index + 2]).map(|hit| hit.as_str()).reduce(|_a, b| b).unwrap();
        // let caps = regex.captures(lines[index + 2]).unwrap().iter().map(|m| m.map_or("", |a| a.as_str())).join("");
        container.push(String::from(caps));
    }
    let flatten = container.iter().map(|m| m.chars()).flatten();
    let sum: u32 = flatten.map(|character| get_priority(&character)).sum();
    println!("Second riddle result is {}", sum)
}

fn build_regex(line: &str) -> Regex {
    let mut regex_string: String = "[".to_string();
    regex_string.push_str(&line.to_string());
    regex_string.push_str("]");
    Regex::new(&regex_string).unwrap()
}

fn get_priority(character: &char) -> u32 {
    let offset = if character.is_lowercase() { 96 } else { 38 };
    *character as u32 - offset
}
