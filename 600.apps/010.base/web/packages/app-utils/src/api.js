import { graphql } from './http';

export async function getDictOptions(dictName) {
  const { DictProvider__getDict } = await graphql(
    `
      query($dictName: !String) {
        DictProvider__getDict(dictName: $dictName) {
          options {
            label
            code
            description
            value
          }
        }
      }
    `,
    { dictName }
  );
  const { options } = DictProvider__getDict;

  return options;
}
