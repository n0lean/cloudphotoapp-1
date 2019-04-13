import os


def parse_app_properties(s):
    name = s[:s.index('=')]
    if name in os.environ:
        ss = '{name}={env_var}'.format(name=name, env_var=os.environ[name])
        return ss
    else:
        return s


if __name__ == '__main__':
    with open('./src/main/resources/application.properties', 'r') as f:
        lines = f.read().splitlines()
    newlines = []
    for l in lines:
        newlines.append(parse_app_properties(l))
    with open('./src/main/resources/application.properties', 'w') as f:
        f.write('\n'.join(newlines))
