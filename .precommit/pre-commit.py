import sys
import os
import re


def get_level(content, level):
    return len(re.compile(r"\[%s\]" % level).findall(content))

def get_file_name(content, postfix=None):
    content = content.replace("\t", " ")
    line_divided = content.split("\n")
    space_divided = [[j for j in i.split(" ") if j.strip()]for i in line_divided]
    filenames = [i[5] for i in space_divided if i]
    if not postfix:
        return filenames
    return [i for i in filenames if ".%s" % postfix in i]

if __name__ == '__main__':
    print('---------------Pre-commit tests---------------')
    #check code command
    command = 'java -jar ' + jarpath[:-1] + ' -c ' + checkfilepath[:-1]

    #the file to check
    files = os.popen('mvn test').read()

    #the result of command
    content = get_file_name(files, 'java')

    error_num = 0
    warning_num = 0
    for i in content:
        error_num += get_level(result,'ERROR')
        warning_num += get_level(result,'WARN')

    print('There is {} warning in your code.'.format(warning_num))

    if error_num > 0:
        print('\n..............You must fix the errors and warnings first, then execute commit command again.....\n')
        sys.exit(-1)
    else:
        print('\n.......................Good job! Exit with 0.........................\n')
        sys.exit(0)