use std::fs;

#[derive(Clone, Copy)]
struct Range {
    start: u32,
    end: u32
}

impl Range {
    fn contains (&self, range: &Range) -> bool{
        self.start <= range.start && self.end >= range.end
    }

    fn contains_each_other(&self, range:Range) -> bool {
        self.contains(&range) || range.contains(self)
    }

    fn overlaps (&self, range: &Range) -> bool{
        self.contains(&range) || self.end >= range.start && self.end <= range.end
    }

    fn overlaps_each_other(&self, range: Range) -> bool {
        self.overlaps(&range) || range.overlaps(self)
    }
}

fn main() {
    let contents = fs::read_to_string("/Users/risc/dev/adventofcode/2022/day4/input.txt")
        .expect("Should have been able to read the file");

    let mut counter: u32 = 0;
    let mut overlap_counter: u32 = 0;
    contents.lines().for_each(|line| {
        let mut split = line.split(",");
        let mut first_elf = split.next().unwrap().split("-");
        let first_elf_range = Range {
            start: first_elf.next().unwrap().parse().unwrap(),
            end: first_elf.next().unwrap().parse().unwrap(),
        };
        let mut second_elf = split.next().unwrap().split("-");
        let second_elf_range = Range {
            start: second_elf.next().unwrap().parse().unwrap(),
            end: second_elf.next().unwrap().parse().unwrap(),
        };
        if first_elf_range.contains_each_other(second_elf_range) {
            counter += 1;
        }
        if first_elf_range.overlaps_each_other(second_elf_range) {
            overlap_counter += 1
        }
    });
    println!("Result is {}", counter);
    println!("Overlap Result is {}", overlap_counter)
}
