KEEP   = 0 # keep original capitalization
UPPER  = 1 # convert to upper case
LOWER  = 2 # convert to lower case
REMOVE = 3 # remove it
ADD    = 4 # add it
TRUNC  = 8 # for UNDERSCORES, if there is more than 1 underscore, remove the extras. for ZERO_XXXX, if there are unneccessary zeros, remove them.
ADD_IF_NEEDED = 16 # for DOUBLE_TYPE_SUFFIX, if REMOVE is also specified and ZERO_FPART is REMOVE, re-add the suffix and remove the trailing decimal point.
# ===== OPTIONS =====

HEX_PREFIX = LOWER
BINARY_PREFIX = LOWER
OCTAL_PREFIX = REMOVE

HEX_LITERAL = UPPER

ZERO_IPART = ADD | TRUNC
ZERO_FPART = ADD | TRUNC

IPART_UNDERSCORES = KEEP
FPART_UNDERSCORES = KEEP

DOUBLE_TYPE_SUFFIX = REMOVE
FLOAT_TYPE_SUFFIX  = UPPER
LONG_TYPE_SUFFIX   = UPPER

EXPONENT_INDICATOR = UPPER
HEX_EXPONENT_INDICATOR = LOWER

EXPONENT_SIGN = KEEP

#####################

assert HEX_PREFIX in (KEEP, UPPER, LOWER, REMOVE), f"Invalid value for HEX_PREFIX: {HEX_PREFIX}"
assert BINARY_PREFIX in (KEEP, UPPER, LOWER, REMOVE), f"Invalid value for BINARY_PREFIX: {BINARY_PREFIX}"
assert OCTAL_PREFIX in (KEEP, REMOVE), f"Invalid value for OCTAL_PREFIX: {OCTAL_PREFIX}"

assert HEX_LITERAL in (KEEP, UPPER, LOWER), f"Invalid value for HEX_LITERAL: {HEX_LITERAL}"

assert ZERO_IPART in (KEEP, ADD, REMOVE, TRUNC, ADD | TRUNC), f"Invalid value for ZERO_IPART: {ZERO_IPART}"
assert ZERO_FPART in (KEEP, ADD, REMOVE, TRUNC, ADD | TRUNC), f"Invalid value for ZERO_FPART: {ZERO_FPART}"

assert IPART_UNDERSCORES in (KEEP, ADD, REMOVE, TRUNC), f"Invalid value for IPART_UNDERSCORES: {IPART_UNDERSCORES}"
assert FPART_UNDERSCORES in (KEEP, ADD, REMOVE, TRUNC), f"Invalid value for FPART_UNDERSCORES: {FPART_UNDERSCORES}"

assert DOUBLE_TYPE_SUFFIX in (KEEP, ADD, REMOVE, UPPER, LOWER, REMOVE | ADD_IF_NEEDED) or DOUBLE_TYPE_SUFFIX.bit_length() == 3 and DOUBLE_TYPE_SUFFIX & (UPPER | LOWER) in (UPPER, LOWER), f"Invalid value for DOUBLE_TYPE_SUFFIX: {DOUBLE_TYPE_SUFFIX}"
assert FLOAT_TYPE_SUFFIX in (KEEP, UPPER, LOWER), f"Invalid value for FLOAT_TYPE_SUFFIX: {FLOAT_TYPE_SUFFIX}"
assert LONG_TYPE_SUFFIX in (KEEP, UPPER, LOWER), f"Invalid value for LONG_TYPE_SUFFIX: {LONG_TYPE_SUFFIX}"

assert EXPONENT_INDICATOR in (KEEP, UPPER, LOWER), f"Invalid value for EXPONENT_INDICATOR: {EXPONENT_INDICATOR}"
assert HEX_EXPONENT_INDICATOR in (KEEP, UPPER, LOWER), f"Invalid value for HEX_EXPONENT_INDICATOR: {HEX_EXPONENT_INDICATOR}"

assert EXPONENT_SIGN in (KEEP, ADD, REMOVE), f"Invalid value for EXPONENT_SIGN: {EXPONENT_SIGN}"

from glob import glob
import re
import os, os.path as path

os.chdir(path.dirname(path.abspath(__file__)))

DIGITS = r'(?:\d+(?:_+\d+)*)'
HEX_DIGITS = r'(?:[a-fA-F0-9]+(?:_+[a-fA-F0-9]+)*)'
BIN_DIGITS = r'(?:[01]+(?:_+[01]+)*)'
OCT_DIGITS = r'(?:[0-7]+(?:_+[0-7]+)*)'
ID_CHAR = r'[a-zA-Z_0-9$]'

# INT_REGEX = re.compile(r'(?<!{ID_CHAR})([1-9]\d*(?:_+\d+)*|0([xX]{HEX_DIGITS}|[bB][01]+(?:_+[01]+)*|_*[0-7]+(?:_+[0-7]+)*))(?!{ID_CHAR})'.format_map(globals()))
# LONG_REGEX = re.compile(r'(?<!{ID_CHAR})([1-9]\d*(?:_+\d+)*|0([xX]{HEX_DIGITS}|[bB][01]+(?:_+[01]+)*|_*[0-7]+(?:_+[0-7]+)*))[lL](?!{ID_CHAR})'.format_map(globals()))
# FLOAT_REGEX = re.compile(r'(?<!{ID_CHAR})((\.{DIGITS}|{DIGITS}(\.{DIGITS}?)?)([eE][-+]?{DIGITS})?|0[xX](\.{HEX_DIGITS}|{HEX_DIGITS}(\.{HEX_DIGITS}?)?)[pP][-+]?{DIGITS})[fF](?!{ID_CHAR})'.format_map(globals()))
# DOUBLE_REGEX = re.compile(r'(?<!{ID_CHAR})((\.{DIGITS}|{DIGITS}(\.{DIGITS}?|(?=[eEdD])))([eE][-+]?{DIGITS})?|0[xX](\.{HEX_DIGITS}|{HEX_DIGITS}(\.{HEX_DIGITS}?)?)[pP][-+]?{DIGITS})[dD]?(?!{ID_CHAR})'.format_map(globals()))

EXPONENT = r'(?:[eE][-+]?{DIGITS})'.format_map(globals())
HEX_EXPONENT = r'(?:[pP][-+]?{DIGITS})'.format_map(globals())

NUMBER_OR_STR_REGEX = re.compile(r'''(?x)
    (["']) (\\ . | [^\\])*? \1
    |
    (?<!{ID_CHAR})
    ( \.{DIGITS}{EXPONENT}?[fFdD]?
    | {DIGITS} ( \.{DIGITS}?{EXPONENT}?[fFdD]?
               | {EXPONENT}[fFdD]?
               | [lLfFdD]?
               )
    | 0[xX] ( \.{HEX_DIGITS}{HEX_EXPONENT}[fFdD]?
            | {HEX_DIGITS} ( \.{HEX_DIGITS}?{HEX_EXPONENT}[fFdD]?
                           | {HEX_EXPONENT}[fFdD]?
                           | [lL]?
                           )
            )
    | 0[bB] {BIN_DIGITS}[lL]?
    )
    (?!{ID_CHAR})
'''.format_map(globals()))

HEX_REGEX = re.compile(r'(?P<prefix>0[xX])(?P<ipart>{HEX_DIGITS})?((?P<decimal>\.)(?P<fpart>{HEX_DIGITS})?)?((?P<xind>[pP])(?P<xpart>[-+]?{DIGITS}))?(?P<suffix>[lLfFdD]?)'.format_map(globals()))
DECIMAL_REGEX = re.compile(r'(?P<ipart>{DIGITS})?((?P<decimal>\.)(?P<fpart>{DIGITS})?)?((?P<xind>[eE])(?P<xpart>[-+]?{DIGITS}))?(?P<suffix>[lLfFdD]?)'.format_map(globals()))
OCTAL_REGEX = re.compile(r'(?P<prefix>0)(?P<ipart>{OCT_DIGITS})(?P<suffix>[lL]?)'.format_map(globals()))
BINARY_REGEX = re.compile(r'(?P<prefix>0[bB])(?P<ipart>{BIN_DIGITS})(?P<suffix>[lL]?)'.format_map(globals()))

UNDERSCORE_REGEX = re.compile(r'_+')
PREFIX_ZEROS = re.compile(r'^0+')
POSTFIX_ZEROS = re.compile(r'0+$')
ZEROS = re.compile(r'^0+$')

# HEX_PREFIX_REGEX = re.compile(r'0[xX]')
# P_REGEX = re.compile(r'[pP]')
# NON_OCTAL_CHAR_REGEX = re.compile(r'[89.eEpPxXbBfFdD]')
# BINARY_REGEX = re.compile(r'0[bB]')

# for num in '1.2', '3', '165_200', '14E-20', '1E10', '7e+2', '0x3A', '0x3AL', '0x3A.p4', '0x3A.Fp-2F', '0x3D_2AF.42P+20':
#     assert NUMBER_REGEX.fullmatch(num), num

# def isfloat(s: str, /) -> bool:
#     if HEX_REGEX.match(s) and not P_REGEX.search(s):
#         return False
#     return s[-1] in 'fF'

# def isdouble(s: str, /) -> bool:
#     if HEX_REGEX.match(s) and not P_REGEX.search(s):
#         return False
#     if '.' in s:
#         return s[-1] not in 'fF'
#     return s[-1] in 'dD'

# def islong(s: str, /) -> bool:
#     return s[-1] in 'lL'

# def isoctal(s: str, /) -> bool:
#     return not NON_OCTAL_CHAR_REGEX.search(s)

replace_count = 0

def refactor(s: str) -> str:
    if s[0] in ('"', "'"):
        return s
    global replace_count
    if (m := HEX_REGEX.fullmatch(s)):        
        result = reformat(
            prefix=m['prefix'],
            ipart=m['ipart'],
            decimal=m['decimal'],
            fpart=m['fpart'],
            xind=m['xind'],
            xpart=m['xpart'],
            suffix=m['suffix']
        )
    elif (m := OCTAL_REGEX.fullmatch(s)):
        result = reformat(
            prefix=m['prefix'],
            ipart=m['ipart'],
            suffix=m['suffix']
        )
    elif (m := BINARY_REGEX.fullmatch(s)):
        result = reformat(
            prefix=m['prefix'],
            ipart=m['ipart'],
            suffix=m['suffix']
        )
    elif (m := DECIMAL_REGEX.fullmatch(s)):
        result = reformat(
            ipart=m['ipart'],
            decimal=m['decimal'],
            fpart=m['fpart'],
            xind=m['xind'],
            xpart=m['xpart'],
            suffix=m['suffix']
        )
    else:
        assert False, f"Could not refactor {s!r}"
    if result != s:
        replace_count += 1
    return result

LEFT = -1
RIGHT = 1

def create_fix_underscores(underscores: int, direction: LEFT|RIGHT=0):
    if underscores == ADD:
        if direction == LEFT:
            def fix_underscores(s: str) -> str:
                if not s: return ""
                for i in range(len(s) - 4, 0, -4):
                    s = s[0:i] + '_' + s[i+1:]
                return s
        elif direction == RIGHT:
            def fix_underscores(s: str) -> str:
                if not s: return ""
                for i in range(3, len(s), 4):
                    s = s[0:i] + '_' + s[i+1:]
                return s
        else:
            assert False, f"Invalid value for direction: {direction}"
    else:
        if underscores == KEEP:
            underscore_replacement = R"\0"
        elif underscores == REMOVE:
            underscore_replacement = ""
        elif underscores == TRUNC:
            underscore_replacement = "_"
        else:
            assert False, f"Invalid value for UNDERSCORES: {underscores}"
        def fix_underscores(s: str) -> str:
            if not s: return ""
            return UNDERSCORE_REGEX.sub(underscore_replacement, s)
    return fix_underscores

fix_ipart_underscores = create_fix_underscores(IPART_UNDERSCORES, direction=LEFT)
fix_fpart_underscores = create_fix_underscores(FPART_UNDERSCORES, direction=RIGHT)

def reformat(*, prefix: str="", ipart: str="", decimal: str="", fpart: str="", xind: str="", xpart: str="", suffix: str="") -> str:
    prefix = prefix or ""
    ipart = fix_ipart_underscores(ipart)
    decimal = decimal or ""
    fpart = fix_fpart_underscores(fpart)
    xind = xind or ""
    xpart = xpart or ""
    suffix = suffix or ""

    assert ipart or fpart, "both ipart and xpart were empty"
    if fpart: assert decimal, "decimal was empty when fpart was not empty"

    if xind: assert xpart, "xpart was empty when xind was not empty"
    else: assert not xpart, "xind was empty when xpart was not empty"

    isdecimal = bool(decimal or fpart or xind or suffix and suffix in "fFdD")

    if isdecimal and not suffix and DOUBLE_TYPE_SUFFIX & ADD:
        if DOUBLE_TYPE_SUFFIX & LOWER:
            assert not DOUBLE_TYPE_SUFFIX & UPPER
            suffix = "d"
        else:
            suffix = "D"
    elif suffix:
        if suffix in "dD" and (DOUBLE_TYPE_SUFFIX == REMOVE or DOUBLE_TYPE_SUFFIX == REMOVE | ADD_IF_NEEDED and (ZERO_FPART != REMOVE or not ZEROS.fullmatch(fpart) or ZERO_IPART != REMOVE or not ZEROS.fullmatch(ipart))):
            suffix = ""
        elif suffix == "d" and DOUBLE_TYPE_SUFFIX.bit_length() <= 3 and DOUBLE_TYPE_SUFFIX & UPPER:
            suffix = "D"
        elif suffix == "D" and DOUBLE_TYPE_SUFFIX.bit_length() <= 3 and DOUBLE_TYPE_SUFFIX & LOWER:
            suffix = "d"
        elif suffix == "f" and FLOAT_TYPE_SUFFIX == UPPER:
            suffix = "F"
        elif suffix == "F" and FLOAT_TYPE_SUFFIX == LOWER:
            suffix = "f"
        elif suffix == "l" and LONG_TYPE_SUFFIX == UPPER:
            suffix = "L"
        elif suffix == "L" and LONG_TYPE_SUFFIX == LOWER:
            suffix = "l"

    if prefix:
        assert prefix[0] == "0" and len(prefix) <= 2
        if len(prefix) == 1: # octal literal
            if OCTAL_PREFIX == REMOVE:
                assert isdecimal == False
                return refactor(f"{int(ipart, base=8)}{suffix}")
            else:
                assert OCTAL_PREFIX == KEEP, OCTAL_PREFIX
        elif prefix[1] in "xX": # hex literal
            if HEX_PREFIX == REMOVE:
                if isdecimal:
                    return refactor(f"{float.fromhex(f'0x{ipart}{decimal}{fpart}{xind}{xpart}')}{suffix}")
                else:
                    return refactor(f"{int(ipart, base=16)}{suffix}")
            elif HEX_PREFIX == UPPER:
                prefix = prefix.upper()
            elif HEX_PREFIX == LOWER:
                prefix = prefix.lower()
            else:
                assert HEX_PREFIX == KEEP, HEX_PREFIX
            if HEX_LITERAL == UPPER:
                ipart = ipart.upper()
                fpart = fpart.upper()
            elif HEX_LITERAL == LOWER:
                ipart = ipart.lower()
                fpart = fpart.lower()
            else:
                assert HEX_LITERAL == KEEP, HEX_LITERAL
            if xind:
                if HEX_EXPONENT_INDICATOR == UPPER:
                    xind = xind.upper()
                elif HEX_EXPONENT_INDICATOR == LOWER:
                    xind = xind.lower()
                else:
                    assert HEX_EXPONENT_INDICATOR == KEEP, HEX_EXPONENT_INDICATOR
        else: # binary literal
            assert prefix[1] in "bB"
            if BINARY_PREFIX == REMOVE:
                assert isdecimal == False
                return refactor(f"{int(ipart, base=2)}{suffix}")
            else:
                assert BINARY_PREFIX == KEEP, BINARY_PREFIX
    else: # decimal literal
        if xind:
            if EXPONENT_INDICATOR == UPPER:
                xind = xind.upper()
            elif EXPONENT_INDICATOR == LOWER:
                xind = xind.lower()
            else:
                assert EXPONENT_INDICATOR == KEEP, EXPONENT_INDICATOR
            if EXPONENT_SIGN == REMOVE and xpart[0] == '+':
                xpart = xpart[1:]
            elif EXPONENT_SIGN == ADD and xpart[0] not in "+-":
                xpart = "+" + xpart
            else:
                assert EXPONENT_SIGN == KEEP, EXPONENT_SIGN

    if isdecimal:
        if not fpart and ZERO_FPART & ADD:
            fpart = "0"
        elif len(fpart) > 1 and fpart[-1] == "0" and ZERO_FPART & TRUNC:
            fpart = fpart[:POSTFIX_ZEROS.search(fpart).start()] or "0"
        elif ZERO_FPART == REMOVE and ZEROS.fullmatch(fpart):
            fpart = ""

        if not ipart and ZERO_IPART & ADD:
            ipart = "0"
            if not fpart and suffix in "dD" and DOUBLE_TYPE_SUFFIX == REMOVE | ADD_IF_NEEDED:
                decimal = ""
        elif len(ipart) > 1 and ipart[0] == "0" and ZERO_IPART & TRUNC:
            ipart = ipart[PREFIX_ZEROS.search(ipart).end():] or "0"
        elif ZERO_IPART == REMOVE and ZEROS.fullmatch(ipart):
            ipart = ""

        if not ipart and not fpart:
            if suffix and DOUBLE_TYPE_SUFFIX == REMOVE | ADD_IF_NEEDED:
                ipart = "0"
                decimal = ""
            else:
                fpart = "0"
                if not suffix or xind: decimal = "."
        elif ipart and fpart:
            decimal = "."

    return f"{prefix}{ipart}{decimal}{fpart}{xind}{xpart}{suffix}"

def replacer(match: re.Match) -> str:
    return refactor(match[0])

if __name__ == "__main__":
    for filename in glob(R'src\main\java\com\**\*.java', recursive=True):
        with open(filename, 'r') as file:
            text = file.read()
        replace_count = 0
        # start = 0
        # i = 0
        # while i < len(text):
        #     if text[i] in ('"', "'"):
        #         if i > start:
        #             text = text[0:start] + NUMBER_REGEX.sub(replacer, text[start:i]) + text[i+1:]
        #         start = None
        #         ch = text[i]
        #         escape = False
        #         i += 1
        #         while i < len(text):
        #             if escape:
        #                 escape = False
        #             elif text[i] == ch:
        #                 start = i + 1
        #                 break
        #             elif text[i] == '\\':
        #                 escape = True
        #             i += 1

        new_text = NUMBER_OR_STR_REGEX.sub(replacer, text)
        if replace_count > 0:
            # with open(filename + '.backup', 'w') as file:
            #     file.write(text)
            with open(filename, 'w') as file:
                file.write(new_text)
            print(f"Replaced {replace_count} occurrence(s) in {filename}")
            # break