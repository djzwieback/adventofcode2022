use std::fs;

struct Game {
    opponent: String,
    my_move: String,
}

fn main() {
    let contents = fs::read_to_string("/Users/risc/dev/adventofcode/2022/day2/input.txt")
        .expect("Should have been able to read the file");
    let split: Vec<Game> = contents.lines().map(
        |s| {
            let splitted_game: Vec<&str> = s.split(" ").collect();
            return Game {
                opponent: String::from(splitted_game[0]),
                my_move: String::from(splitted_game[1]),
            };
        }
    ).collect();
    println!("Number of games {}", split.len());
    let mut end_result: u32 = 0;
    for game in split {
        end_result += evaluate(game.opponent, game.my_move)
    }
    println!("The end result is {}", end_result)
}

// A ROCK X ROCK
// B PAPER Y PAPER
// C SCISSOR Z SCISSOR
fn evaluate(opponent: String, my_move: String) -> u32 {
    let mut result: u32 = 0;
    match opponent.as_str() {
        "A" => {
            if my_move == "X" {
                result += 3
            } else if my_move == "Y" {
                result += 6
            }
        }
        "B" => {
            if my_move == "Y" {
                result += 3
            } else if my_move == "Z" {
                result += 6
            }
        }
        "C" => {
            if my_move == "Z" {
                result += 3
            } else if my_move == "X" {
                result += 6
            }
        }
        _ => ()
    }
    // println!("Opponent: {}, MyMove: {}, result: {}", opponent, my_move, result);
    let string = my_move.clone();
    result += evaluate_my_move(my_move);
    println!("Opponent: {}, MyMove: {}, result: {}", opponent, string, result);
    result
}
// A ROCK X ROCK
// B PAPER Y PAPER
// C SCISSOR Z SCISSOR
fn evaluate_my_move(my_move: String) -> u32 {
    match my_move.as_str() {
        "X" => 1,
        "Y" => 2,
        "Z" => 3,
        _ => 0
    }
}
