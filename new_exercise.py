import os
import re
import argparse
from typing import Sequence, Callable


def fpath_to_ns(fpath: str) -> str:
    sep = os.sep
    return fpath.strip(f" {sep}").replace(sep, ".").replace("_", "-")


def ns_to_fpath(ns: str) -> str:
    sep = os.sep
    return ns.strip(" .").replace(".", sep).replace("-", "_")


REPO_NAME = "sicp-in-clojure"
REPO_DIR = os.path.realpath(os.path.dirname(__file__))
SOLUTION_DIR = os.path.join(REPO_DIR, "src", ns_to_fpath(REPO_NAME))
TEST_DIR = os.path.join(REPO_DIR, "test", ns_to_fpath(REPO_NAME))


def process_ex_str(exstr: str) -> str:
    exstr = exstr.replace("_", ".").replace("-", ".")
    exstr = re.sub(r"[^0-9\.]", "", exstr).strip(".")
    parts = exstr.split('.')
    last_pos = len(parts) - 1
    parts[last_pos] = parts[last_pos].zfill(2)
    return '.'.join(parts)


def solution_fname(exstr: str) -> str:
    return "ex" + exstr.replace(".", "_") + ".clj"


def test_fname(exstr: str) -> str:
    return re.sub(r".clj$", "_test.clj", solution_fname(exstr))


def solution_fpath(exstr: str) -> str:
    return os.path.join(SOLUTION_DIR, solution_fname(exstr))


def test_fpath(exstr: str) -> str:
    return os.path.join(TEST_DIR, test_fname(exstr))


def to_namespace(fname: str) -> str:
    ns = fname.replace("_", "-")
    return re.sub(r".clj$", "", ns)


def solution_template(exstr: str) -> str:
    ns = to_namespace(solution_fname(exstr))
    content = f"""(ns {REPO_NAME}.{ns})
    """
    return content


def test_template(exstr: str) -> str:
    ns = to_namespace(test_fname(exstr))
    sol_ns = to_namespace(solution_fname(exstr))

    content = f"""(ns {REPO_NAME}.{ns}
  (:require [clojure.test :refer :all]
            [{REPO_NAME}.{sol_ns} :refer :all]))

(deftest changethis
  (testing ""
    (is true)))
    """
    return content


def write_template(
    exstr: str, get_content: Callable[[str], str], get_fpath: Callable[[str], str]
) -> None:
    fpath = get_fpath(exstr)
    if os.path.exists(fpath):
        print(f"{fpath} already exists! skipping...")
        return
    print(fpath)
    content = get_content(exstr)
    print(content)
    with open(fpath, 'w') as f:
        f.write(content)


def add_exercise_template(exstr: str) -> None:
    exstr = process_ex_str(exstr)
    write_template(exstr, solution_template, solution_fpath)
    write_template(exstr, test_template, test_fpath)


def main(exlist: Sequence[str]):
    list(map(add_exercise_template, exlist))
    return


if __name__ == "__main__":
    _parser = argparse.ArgumentParser()
    _parser.add_argument(
        "exercises",
        type=str,
        nargs="+",
        help='The exercises to be added. For example: "2.15" "1.03"',
    )
    args = _parser.parse_args()
    main(args.exercises)
