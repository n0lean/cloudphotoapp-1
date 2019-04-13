# CloudPhoto ![Travis](https://travis-ci.com/Dennisan96/cloudphotoapp.svg?token=oqwGsea6rix3rBswsd6W&branch=master)[![codecov](https://codecov.io/gh/n0lean/cloudphotoapp-1/branch/master/graph/badge.svg?token=f19NSI582p)](https://codecov.io/gh/n0lean/cloudphotoapp-1)

Backend repo for CloudPhoto project.

## Set your Git pre-commit hook for unit tests
Run this
```bash
ln -s .precommit/pre-commit.py .git/hooks/pre-commit
```

## Set your application.properties in your ENV Variable.

E.g.
```properties

amazon.dynamodb.region=us-east-1
amazon.dynamodb.endpoint=http://localhost:8000/
# OR amazon.dynamodb.endpoint=http://dynamodb.us-west-1.amazonaws.com
AWS_ACCESS_KEY=aaa
AWS_ACCESS_KEY_ID=aaa

```


