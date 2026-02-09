export function auth(fastify, { token }) {
  if (!token) {
    throw new Error('[In Risk] No token specified for the server');
  }

  fastify.addHook('preHandler', (request, reply, done) => {
    const t = request.headers.authorization?.replace('Bearer ', '');

    if (t !== token) {
      reply.code(401).send({ error: 'Unauthorized' });
    } else {
      done();
    }
  });
}
