import os


def parse_app_properties(s):
    if '$' in s:
        name = s[:s.index('=') + 1]
        var_name = s[s.index('{') + 1: s.index('}')]
        try:
            ss = '{name}={env_var}'.format(name=name, env_var=os.environ[var_name])
        except KeyError:
            print('ERROR: ENV variable {} not found.'.format(var_name))
            raise
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
